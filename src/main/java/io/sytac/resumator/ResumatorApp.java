package io.sytac.resumator;

import com.google.common.eventbus.AsyncEventBus;
import io.sytac.resumator.security.Oauth2AuthenticationFilter;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	public static void main(String[] args) throws IOException {
        final ResumatorApp app = new ResumatorApp();
        app.banner();

        final Configuration configuration = app.loadConfiguration();
        final ResourceConfig rc = app.constructConfig(configuration);

        app.startServer(configuration, rc);
    }

    protected ResourceConfig constructConfig(final Configuration configuration) {
        ResourceConfig rc = registerApplicationResorces(new ResourceConfig());
        rc = registerConfiguration(rc, configuration);
        rc = registerEventBus(rc, configuration);
        rc = registerJSONSupport(rc);
        rc = registerSecurity(rc);
        return rc;
    }

    private ResourceConfig registerSecurity(ResourceConfig rc) {
        return rc.register(RolesAllowedDynamicFeature.class)
                    .register(Oauth2AuthenticationFilter.class);
    }

    private ResourceConfig registerJSONSupport(ResourceConfig rc) {
        return rc.register(ObjectMapperResolver.class);
    }

    protected ResourceConfig registerEventBus(final ResourceConfig rc, final Configuration configuration) {
        final String id = configuration.getProperty("resumator.logs.events.tag").orElse("resumator");
        final ExecutorService executor = createExecutorService(configuration);
        final AsyncEventBus eventBus = new AsyncEventBus(id, executor);
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(eventBus);
            }
        });
    }

    protected ExecutorService createExecutorService(Configuration configuration) {
        final Integer concurrency = configuration.getIntegerProperty("resumator.sys.threadpool.size").orElse(1);
        return Executors.newFixedThreadPool(concurrency);
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
                "io.sytac.resumator.http",                // Resumator
                "com.theoryinpractise.halbuilder.jaxrs"); // HAL support
    }

    protected Configuration loadConfiguration() {
        LOGGER.info("Loading the configuration");
        return new Configuration();

    }

    protected Server startServer(final Configuration configuration, final ResourceConfig rc) {
        final Optional<URI> maybeURI = configuration.getURIProperty("resumator.http.uri");
        final URI uri = maybeURI.orElseGet(() -> {
            try {
                return new URI("http://localhost:8080");
            } catch (URISyntaxException e) {
                // NOP
            }

            return null; // never happens
        });

        return JettyHttpContainerFactory.createServer(uri, rc);
    }

    protected void banner() {
        LOGGER.info("╔╦╗┬ ┬┌─┐  ╦═╗┌─┐┌─┐┬ ┬┌┬┐┌─┐┌┬┐┌─┐┬─┐");
        LOGGER.info(" ║ ├─┤├┤   ╠╦╝├┤ └─┐│ ││││├─┤ │ │ │├┬┘");
        LOGGER.info(" ╩ ┴ ┴└─┘  ╩╚═└─┘└─┘└─┘┴ ┴┴ ┴ ┴ └─┘┴└─");
        LOGGER.info("───────────────────────────────── v0.1");
    }
}
