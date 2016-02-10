package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.model.Event;
import io.sytac.resumator.organization.NewOrganizationCommand;
import io.sytac.resumator.organization.Organization;

import java.io.IOException;

/**
 * Executes 'NewOrganization' events
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class NewOrganizationEventExecutor extends AbstractEventExecutor {

    public NewOrganizationEventExecutor(final ReplayEventConfig config) {
        super(config);
    }

    @Override
    public void execute(Event event) throws IOException {
        final NewOrganizationCommand command = config.getObjectMapper().readValue(event.getPayload(), NewOrganizationCommand.class);
        final String orgName = command.getPayload().getName();
        final String domain = command.getPayload().getDomain();
        config.getOrganizationRepository().addOrganization(new Organization(orgName, domain));
    }
}