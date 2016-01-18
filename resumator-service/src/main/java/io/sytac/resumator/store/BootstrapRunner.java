package io.sytac.resumator.store;


import io.sytac.resumator.organization.NewOrganizationCommand;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Date;
import java.util.Optional;

/**
 * Bootstrap process initializer
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class BootstrapRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapRunner.class);

    @Inject
    public BootstrapRunner(final Bootstrap bootstrap, final OrganizationRepository orgs) {
        bootstrap.start(result -> {
            LOGGER.warn("Bootstrap finished");
            if (result.getFailures() != null && result.getFailures().size() == 0) {
                LOGGER.warn(" with no failures");
            } else {
                LOGGER.warn(" with failures: ");
                result.getFailures().forEach((key, fail) -> LOGGER.info(fail.getMessage()));
            }
        });

        // Register organisation manually. Will be removed when NewOrganisation endpoint is implemented.
        NewOrganizationCommand organizationCommand = new NewOrganizationCommand("Sytac", "sytac.io", String.valueOf(new Date().getTime()));
        final Optional<Organization> organization = orgs.fromDomain(organizationCommand.getPayload().getDomain());
        if (!organization.isPresent()) {
            orgs.register(organizationCommand);
        }
    }
}
