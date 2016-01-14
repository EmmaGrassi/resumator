package io.sytac.resumator.store;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.organization.OrganizationRepository;
import org.glassfish.hk2.api.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Replays events to build up the in-memory query state
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class BootstrapRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapRunner.class);

    @Inject
    public BootstrapRunner(final Bootstrap bootstrap) {
        bootstrap.start(result -> {
            LOGGER.info("Bootstrap finished");
            if (result.getFailures() != null && result.getFailures().size() == 0) {
                LOGGER.info(" with no failures");
            } else {
                LOGGER.info(" with failures: ");
                result.getFailures().forEach((key, fail) -> LOGGER.info(fail.getMessage()));
            }
        });
    }
}
