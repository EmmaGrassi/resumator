package io.sytac.resumator.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.organization.NewOrganizationCommand;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Replays events to build up the in-memory query state
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Bootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    private final EventStore store;
    private final OrganizationRepository orgs;
    private final ObjectMapper json;

    @Inject
    public Bootstrap(final EventStore store, final OrganizationRepository orgs, final ObjectMapper json) {
        this.store = store;
        this.orgs = orgs;
        this.json = json;
    }

    public void replay() {
        LOGGER.info("Replaying events, setting store to read-only");
        store.setReadOnly(true);
        store.getAll().forEach(this::replayEvent);

        LOGGER.info("Replayed events successfully, setting store to read-write");
        store.setReadOnly(false);
    }

    private void replayEvent(Event event) {
        try {
            switch (event.getType()) {
                case "newEmployee":
                    final NewEmployeeCommand command = json.readValue(event.getPayload(), NewEmployeeCommand.class);
                    orgs.fromDomain(command.getPayload().getOrganizationDomain())
                            .orElseThrow(() -> new IllegalStateException("Could not replay the following event because the organization is unknown: " + event))
                            .addEmployee(command);
                    break;

                case "newOrganization":
                    final NewOrganizationCommand organizationCommand = json.readValue(event.getPayload(), NewOrganizationCommand.class);
                    Organization organization = new Organization(organizationCommand.getPayload().getName(), organizationCommand.getPayload().getDomain());
                    orgs.addOrganization(organization);
                    break;

                default:
                    throw new IllegalStateException("Could not format the following unknown event: " + event);
            }
        } catch (IOException e) {
            throw new IllegalStateException("The following exception occurred while replaying events: ", e);
        }
    }
}
