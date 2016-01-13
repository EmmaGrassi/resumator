package io.sytac.resumator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.events.EventPublisherFactory;
import io.sytac.resumator.events.LocalEventPublisher;
import io.sytac.resumator.http.UriRewriteSupportFilter;
import io.sytac.resumator.organization.InMemoryOrganizationRepository;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Oauth2AuthenticationFilter;
import io.sytac.resumator.security.Oauth2SecurityService;
import io.sytac.resumator.security.Oauth2SecurityServiceFactory;
import io.sytac.resumator.store.Bootstrap;
import io.sytac.resumator.store.EventStore;
import io.sytac.resumator.store.sql.SchemaManager;
import io.sytac.resumator.store.sql.SqlStore;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
public class ResumatorApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResumatorApp.class);

	public static void main(final String[] args) throws IOException {
        final ResumatorApp app = new ResumatorApp();
        app.banner();

        final Configuration configuration = app.loadConfiguration();
        final ResourceConfig rc = app.constructConfig(configuration);

        try {
            app.createServer(configuration, rc).start();


            /*ObjectMapper objectMapper = new ObjectMapper().registerModule(new Jdk8Module());
            EventPublisher eventPublisher = new LocalEventPublisher(configuration, objectMapper);
            SqlStore store = new SqlStore(configuration, eventPublisher);
//            store.setReadOnly(false);

            new SchemaManager(configuration, store).migrate();

            Bootstrap bootstrap = new Bootstrap(store, new InMemoryOrganizationRepository(), objectMapper);
            bootstrap.start(result -> LOGGER.info("Bootstrap started"));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ResourceConfig constructConfig(final Configuration configuration) {
        ResourceConfig rc = registerApplicationResources(new ResourceConfig());
        rc = registerConfiguration(rc, configuration);
        rc = registerEventPublisher(rc);
        rc = registerCommandFactory(rc);
        rc = registerJSONSupport(rc);
        rc = registerUriRewriteSupport(rc);
        rc = registerSecurity(rc);
        rc = registerRepositories(rc);
        return rc;
    }

    private ResourceConfig registerRepositories(final ResourceConfig rc) {
        return rc.register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(InMemoryOrganizationRepository.class).to(OrganizationRepository.class);
                    }
                });
    }

    private ResourceConfig registerSecurity(final ResourceConfig rc) {
        return rc.register(Oauth2AuthenticationFilter.class)
                 .register(RolesAllowedDynamicFeature.class)
                 .register(new AbstractBinder() {
                     @Override
                     protected void configure() {
                         bindFactory(Oauth2SecurityServiceFactory.class).to(Oauth2SecurityService.class);
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
                bindFactory(ObjectMapperResolver.class).to(ObjectMapper.class).in(Singleton.class);
            }
        });
    }

    protected ResourceConfig registerEventPublisher(final ResourceConfig rc) {
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(EventPublisherFactory.class).to(EventPublisher.class).in(Singleton.class);
            }
        });
    }

    protected ResourceConfig registerCommandFactory(final ResourceConfig rc) {
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(CommandFactoryResolver.class).to(CommandFactory.class).in(Singleton.class);
            }
        });
    }

    protected ResourceConfig registerConfiguration(final ResourceConfig rc, final Configuration configuration) {
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(configuration);
            }
        });
    }

    protected ResourceConfig registerApplicationResources(final ResourceConfig rc) {
        return rc.packages(
                "io.sytac.resumator.employee",
                "io.sytac.resumator.organization",
                "io.sytac.resumator.service",
                "com.theoryinpractise.halbuilder.jaxrs"); // HAL support
    }

    protected Configuration loadConfiguration() {
        LOGGER.info("Loading the configuration");
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
        LOGGER.info("╔╦╗┬ ┬┌─┐  ╦═╗┌─┐┌─┐┬ ┬┌┬┐┌─┐┌┬┐┌─┐┬─┐");
        LOGGER.info(" ║ ├─┤├┤   ╠╦╝├┤ └─┐│ ││││├─┤ │ │ │├┬┘");
        LOGGER.info(" ╩ ┴ ┴└─┘  ╩╚═└─┘└─┘└─┘┴ ┴┴ ┴ ┴ └─┘┴└─");
        LOGGER.info("───────────────────────────────── v0.1");
    }
}
