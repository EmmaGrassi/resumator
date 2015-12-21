package io.sytac.resumator;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.events.EventPublisherFactory;
import io.sytac.resumator.http.UriRewriteSupportFilter;
import io.sytac.resumator.organization.InMemoryOrganizationRepository;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Oauth2AuthenticationFilter;
import io.sytac.resumator.security.Oauth2SecurityService;
import io.sytac.resumator.security.Oauth2SecurityServiceFactory;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;

import static io.sytac.resumator.ConfigurationEntries.BASE_URI;

/**
 *  ╔╦╗┬ ┬┌─┐  ╦═╗┌─┐┌─┐┬ ┬┌┬┐┌─┐┌┬┐┌─┐┬─┐TM
 *   ║ ├─┤├┤   ╠╦╝├┤ └─┐│ ││││├─┤ │ │ │├┬┘
 *   ╩ ┴ ┴└─┘  ╩╚═└─┘└─┘└─┘┴ ┴┴ ┴ ┴ └─┘┴└─
 *
 * The entry point to the Resumator&trade;. Making consultanting life easier.
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class ResumatorApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResumatorApp.class);

	public static void main(final String[] args) throws IOException {
        final ResumatorApp app = new ResumatorApp();
        app.banner();
        for(String arg : Arrays.asList(args)) {
            LOGGER.info("Started with: " + arg);
        }

        final Configuration configuration = app.loadConfiguration();
        final ResourceConfig rc = app.constructConfig(configuration);

        try {
            app.createServer(configuration, rc).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ResourceConfig constructConfig(final Configuration configuration) {
        ResourceConfig rc = registerApplicationResorces(new ResourceConfig());
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

    protected ResourceConfig registerApplicationResorces(final ResourceConfig rc) {
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
