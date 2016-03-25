package io.sytac.resumator.command;

import io.sytac.resumator.employee.*;
import io.sytac.resumator.organization.NewOrganizationCommand;

import java.util.Map;

/**
 * Creates command descriptors and publishes the related event
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class CommandFactory {

    public NewEmployeeCommand newEmployeeCommand(final EmployeeCommandPayload payload, final String domain,final String userName) {
        final CommandHeader header = new CommandHeader.Builder().setDomain(domain).setUserName(userName).build();
        return new NewEmployeeCommand(header, payload);
    }

    public UpdateEmployeeCommand updateEmployeeCommand(final String employeeId,
                                                       final EmployeeCommandPayload payload,
                                                       final String domain,final String userName) {

        final CommandHeader header = new CommandHeader.Builder().setId(employeeId).setUserName(userName).setDomain(domain).build();
        return new UpdateEmployeeCommand(header, payload);
    }

    public RemoveEmployeeCommand removeEmployeeCommand(final String employeeId, final String domain,final String userName) {
        final CommandHeader header = new CommandHeader.Builder().setId(employeeId).setDomain(domain).setUserName(userName).build();
        return new RemoveEmployeeCommand(header, new RemoveEmployeeCommandPayload(employeeId));
    }

    public NewOrganizationCommand newOrganizationCommand(final Map<String, String> input) {
        final String name = input.get("name");
        final String domain = input.get("domain");
        final String timestamp = input.get("timestamp");
        return new NewOrganizationCommand(name, domain, timestamp);
    }
}
