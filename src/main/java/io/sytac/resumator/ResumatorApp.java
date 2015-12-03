package io.sytac.resumator;

import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

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
        banner();

        ResourceConfig rc = registerApplicationResorces(new ResourceConfig());
        final Configuration configuration = loadConfiguration();
        rc = registerConfiguration(rc, configuration);
        final Server server = startServer(configuration, rc);
    }

    private static ResourceConfig registerConfiguration(final ResourceConfig rc, final Configuration configuration) {
        return rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(configuration);
            }
        });
    }

    protected static ResourceConfig registerApplicationResorces(final ResourceConfig rc) {
        return rc.packages(
                "io.sytac.resumator.http",                // Resumator
                "com.theoryinpractise.halbuilder.jaxrs"); // HAL support
    }

    private static Configuration loadConfiguration() {
        LOGGER.info("Loading the configuration");
        return new Configuration();

    }

    private static Server startServer(final Configuration configuration, final ResourceConfig rc) {
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

    private static void banner() {
        LOGGER.info("╔╦╗┬ ┬┌─┐  ╦═╗┌─┐┌─┐┬ ┬┌┬┐┌─┐┌┬┐┌─┐┬─┐");
        LOGGER.info(" ║ ├─┤├┤   ╠╦╝├┤ └─┐│ ││││├─┤ │ │ │├┬┘");
        LOGGER.info(" ╩ ┴ ┴└─┘  ╩╚═└─┘└─┘└─┘┴ ┴┴ ┴ ┴ └─┘┴└─");
        LOGGER.info("───────────────────────────────── v0.1");
    }
}
