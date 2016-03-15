package io.sytac.resumator.store.bootstrap;


import io.sytac.resumator.exception.ResumatorInternalException;
import io.sytac.resumator.organization.NewOrganizationCommand;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

import java.util.Date;
import java.util.Optional;

/**
 * Bootstrap process initializer
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Slf4j
public class BootstrapRunner {

    @Inject
    public BootstrapRunner(final Bootstrap bootstrap, final OrganizationRepository orgs) {
        try {
	    bootstrap.replay();
	} catch (ResumatorInternalException e) {
	    log.error("Bootsrap failed due to the exception: "+e.getCause()+" Exiting the app.");
	    System.exit(0);
	}

        // Register organisation manually. Will be removed when NewOrganisation endpoint is implemented.
        NewOrganizationCommand organizationCommand = new NewOrganizationCommand("Sytac", "sytac.io", String.valueOf(new Date().getTime()));
        final Optional<Organization> organization = orgs.fromDomain(organizationCommand.getPayload().getDomain());
        if (!organization.isPresent()) {
            orgs.register(organizationCommand);
            log.debug("Added dummy organization manually");
        }
    }
}
