package io.sytac.resumator.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.command.Command;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.RemoveEmployeeCommand;
import io.sytac.resumator.employee.UpdateEmployeeCommand;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.organization.NewOrganizationCommand;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;

/**
 * Replays events to build up the in-memory query state
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Slf4j
public class Bootstrap {

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
        log.info("Replaying events, setting store to read-only");
        store.setReadOnly(true);
        store.getAll().forEach(this::replayEvent);

        log.info("Replayed events successfully, setting store to read-write");
        store.setReadOnly(false);
    }

    private void checkIdAndDomainInHeader(final Command command) {
        final Optional<String> id = command.getHeader().getId();
        final Optional<String> domain = command.getHeader().getDomain();
        if (!id.isPresent() || !domain.isPresent()) {
            throw new IllegalStateException("Cannot replay 'newEmployee' event without id and domain information in the header");
        }
    }

    private Organization getOrganization(final Command command) {
        return orgs.fromDomain(command.getHeader().getDomain().get())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Cannot replay '%s' for unknown organization", command.getType())));
    }

    private void replayEvent(Event event) {
        try {
            switch (event.getType()) {
                case NewEmployeeCommand.EVENT_TYPE:
                    final NewEmployeeCommand newEmployeeCommand = json.readValue(event.getPayload(), NewEmployeeCommand.class);
                    checkIdAndDomainInHeader(newEmployeeCommand);
                    this.getOrganization(newEmployeeCommand).addEmployee(newEmployeeCommand);
                break;

                case UpdateEmployeeCommand.EVENT_TYPE:
                    final UpdateEmployeeCommand updateEmployeeCommand = json.readValue(event.getPayload(), UpdateEmployeeCommand.class);
                    checkIdAndDomainInHeader(updateEmployeeCommand);
                    this.getOrganization(updateEmployeeCommand).updateEmployee(updateEmployeeCommand);
                break;

                case RemoveEmployeeCommand.EVENT_TYPE:
                    final RemoveEmployeeCommand removeEmployeeCommand = json.readValue(event.getPayload(), RemoveEmployeeCommand.class);
                    checkIdAndDomainInHeader(removeEmployeeCommand);
                    this.getOrganization(removeEmployeeCommand).removeEmployee(removeEmployeeCommand);
                break;

                case NewOrganizationCommand.EVENT_TYPE:
                    final NewOrganizationCommand organizationCommand = json.readValue(event.getPayload(), NewOrganizationCommand.class);
                    final Organization organization = new Organization(organizationCommand.getPayload().getName(), organizationCommand.getPayload().getDomain());
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
