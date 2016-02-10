package io.sytac.resumator.command;

import io.sytac.resumator.employee.*;
import io.sytac.resumator.organization.NewOrganizationCommand;
import io.sytac.resumator.user.ProfileCommandPayload;
import io.sytac.resumator.user.NewProfileCommand;

import java.util.Map;
import java.util.UUID;

/**
 * Creates command descriptors and publishes the related event
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class CommandFactory {

    public NewEmployeeCommand newEmployeeCommand(final EmployeeCommandPayload payload, final String domain) {
        final CommandHeader header = CommandHeader.builder().id(UUID.randomUUID().toString()).domain(domain).build();
        return new NewEmployeeCommand(header, payload);
    }

    public UpdateEmployeeCommand updateEmployeeCommand(final String employeeId,
                                                       final EmployeeCommandPayload payload,
                                                       final String domain) {

        final CommandHeader header = CommandHeader.builder().id(employeeId).domain(domain).build();
        return new UpdateEmployeeCommand(header, payload);
    }

    public RemoveEmployeeCommand removeEmployeeCommand(final String employeeId, final String domain) {
        final CommandHeader header = CommandHeader.builder().id(employeeId).domain(domain).build();
        return new RemoveEmployeeCommand(header, new RemoveEmployeeCommandPayload(employeeId));
    }

    public NewOrganizationCommand newOrganizationCommand(final Map<String, String> input) {
        final String name = input.get("name");
        final String domain = input.get("domain");
        final String timestamp = input.get("timestamp");
        return new NewOrganizationCommand(name, domain, timestamp);
    }

    public NewProfileCommand newProfileCommand(final ProfileCommandPayload payload) {
        final CommandHeader header = CommandHeader.builder().id(UUID.randomUUID().toString()).build();
        return new NewProfileCommand(header, payload);
    }
}
