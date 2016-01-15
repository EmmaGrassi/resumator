package io.sytac.resumator.store;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Bootstrap process initializer
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class BootstrapRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapRunner.class);

    @Inject
    public BootstrapRunner(final Bootstrap bootstrap) {
        bootstrap.start(result -> {
            LOGGER.warn("Bootstrap finished");
            if (result.getFailures() != null && result.getFailures().size() == 0) {
                LOGGER.warn(" with no failures");
            } else {
                LOGGER.warn(" with failures: ");
                result.getFailures().forEach((key, fail) -> LOGGER.info(fail.getMessage()));
            }
        });
    }
}
