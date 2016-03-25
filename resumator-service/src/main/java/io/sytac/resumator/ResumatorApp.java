package io.sytac.resumator;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.docx.DocxGenerator;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.events.EventPublisherFactory;
import io.sytac.resumator.http.UriRewriteSupportFilter;
import io.sytac.resumator.organization.InMemoryOrganizationRepository;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.*;
import io.sytac.resumator.store.bootstrap.Bootstrap;
import io.sytac.resumator.store.bootstrap.BootstrapRunner;
import io.sytac.resumator.store.bootstrap.Migrator;
import io.sytac.resumator.store.EventStore;
import io.sytac.resumator.store.sql.SchemaManager;
import io.sytac.resumator.store.sql.SqlStore;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static io.sytac.resumator.ConfigurationEntries.BASE_URI;

/**
 *  ╔╦╗┬ ┬┌─┐  ╦═╗┌─┐┌─┐┬ ┬┌┬┐┌─┐┌┬┐┌─┐┬─┐TM
 *   ║ ├─┤├┤   ╠╦╝├┤ └─┐│ ││││├─┤ │ │ │├┬┘
 *   ╩ ┴ ┴└─┘  ╩╚═└─┘└─┘└─┘┴ ┴┴ ┴ ┴ └─┘┴└─
 *
 * The entry point to the Resumator&trade;. Making consulting life easier.
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Slf4j
public class ResumatorApp {

    public static void main(final String[] args) throws IOException {
        final ResumatorApp app = new ResumatorApp();
        app.banner();

        final Configuration configuration = app.loadConfiguration();
        final ResourceConfig rc = app.constructConfig(configuration);

        try {
            app.createServer(configuration, rc).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ResourceConfig constructConfig(final Configuration configuration) {
        ResourceConfig rc = registerApplicationResources(new ResourceConfig());
        rc = registerConfiguration(rc, configuration);
        rc = registerLoggingBridge(rc);
        rc = registerEventPublisher(rc);
        rc = registerCommandFactory(rc);
        rc = registerJSONSupport(rc);
        rc = registerInclusiveJSONSupport(rc);
        rc = registerUriRewriteSupport(rc);
        rc = registerSecurity(rc);
        rc = registerRepositories(rc);
        rc = registerDocxGenerator(rc);
        rc = registerGlobalExceptionHandler(rc);
        rc = registerBootstrap(rc);
        return rc;
    }

    private ResourceConfig registerLoggingBridge(final ResourceConfig rc) {
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(LoggingBridge.class).in(Immediate.class);
            }
        });
    }

    private ResourceConfig registerGlobalExceptionHandler(final ResourceConfig rc) {
        return rc.register(WebApplicationExceptionMapper.class)
                 .register(GlobalExceptionMapper.class);
    }

    private ResourceConfig registerDocxGenerator(final ResourceConfig rc) {
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(DocxGenerator.class).to(DocxGenerator.class);
            }
        });
    }

    private ResourceConfig registerBootstrap(final ResourceConfig rc) {
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(Migrator.class).to(Migrator.class).in(Singleton.class);
                bind(Bootstrap.class).to(Bootstrap.class).in(Singleton.class);
                bind(BootstrapRunner.class).in(Immediate.class);
            }
        });
    }

    private ResourceConfig registerRepositories(final ResourceConfig rc) {
        return rc.register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(InMemoryOrganizationRepository.class).to(OrganizationRepository.class).in(Singleton.class);
                        bind(SqlStore.class).to(EventStore.class).in(Immediate.class);
                        bind(SchemaManager.class).in(Immediate.class);
                    }
                });
    }

    private ResourceConfig registerSecurity(final ResourceConfig rc) {
        return rc.register(Oauth2AuthenticationFilter.class)
                 .register(XsrfValidationFilter.class)
                 .register(RolesAllowedDynamicFeature.class)
                 .register(new AbstractBinder() {
                     @Override
                     protected void configure() {
                         bindFactory(AuthenticationServiceFactory.class)
                                 .to(AuthenticationService.class);
                         
                         bindFactory(Oauth2SecurityServiceFactory.class)
                         .to(Oauth2SecurityService.class);

                         bind(UserPrincipalFactoryProvider.class)
                                 .to(ValueFactoryProvider.class)
                                 .in(Singleton.class);
                         bind(UserPrincipalParamResolver.class)
                                 .to(new TypeLiteral<InjectionResolver<UserPrincipal>>() {})
                                 .in(Singleton.class);
                     }
                 });
    }

    private ResourceConfig registerUriRewriteSupport(final ResourceConfig rc) {
        return rc.register(UriRewriteSupportFilter.class);
    }

    private ResourceConfig registerJSONSupport(final ResourceConfig rc) {
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(ObjectMapperFactory.class).to(ObjectMapper.class).in(Singleton.class);
            }
        });
    }
    
    //This is needed to deserialize JSON inputs into Java Objects when the data is sent to the server.
    private ResourceConfig registerInclusiveJSONSupport(final ResourceConfig rc) {
	    return rc.register(ObjectMapperProvider.class).register(JacksonFeature.class);
        
        }

    private ResourceConfig registerEventPublisher(final ResourceConfig rc) {
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(EventPublisherFactory.class).to(EventPublisher.class).in(Singleton.class);
            }
        });
    }

    private ResourceConfig registerCommandFactory(final ResourceConfig rc) {
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(CommandFactoryResolver.class).to(CommandFactory.class).in(Singleton.class);
            }
        });
    }

    private ResourceConfig registerConfiguration(final ResourceConfig rc, final Configuration configuration) {
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(configuration);
            }
        });
    }

    private ResourceConfig registerApplicationResources(final ResourceConfig rc) {
        return rc.packages(
                "io.sytac.resumator.employee",
                "io.sytac.resumator.organization",
                "io.sytac.resumator.service",
                "com.theoryinpractise.halbuilder.jaxrs") // HAL support
                .register(ImmediateScopeFeature.class);  // enable immediate instantiation of beans
    }

    protected Configuration loadConfiguration() {
        log.info("Loading the configuration");
        return new Configuration();

    }

    protected Server createServer(final Configuration configuration, final ResourceConfig rc) {
        final Optional<URI> maybeURI = configuration.getURIProperty(BASE_URI);
        final URI uri = maybeURI.orElseGet(() -> {
            try {
                return new URI("http://localhost:9090");
            } catch (URISyntaxException e) {
                // NOP
            }

            return null; // never happens
        });

        return JettyHttpContainerFactory.createServer(uri, rc, false);
    }

    protected void banner() {
        log.info("╔╦╗┬ ┬┌─┐  ╦═╗┌─┐┌─┐┬ ┬┌┬┐┌─┐┌┬┐┌─┐┬─┐");
        log.info(" ║ ├─┤├┤   ╠╦╝├┤ └─┐│ ││││├─┤ │ │ │├┬┘");
        log.info(" ╩ ┴ ┴└─┘  ╩╚═└─┘└─┘└─┘┴ ┴┴ ┴ ┴ └─┘┴└─");
        log.info("───────────────────────────────── v0.1");
    }
}