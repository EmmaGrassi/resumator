package io.sytac.resumator.command;

import io.sytac.resumator.employee.*;
import io.sytac.resumator.organization.NewOrganizationCommand;

import java.util.Map;
import java.util.UUID;

/**
 * Creates command descriptors and publishes the related event
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class CommandFactory {

    public NewEmployeeCommand newEmployeeCommand(final NewEmployeeCommandPayload payload, String domain) {
        return new NewEmployeeCommand(payload,
                new CommandHeader.Builder()
                        .setId(UUID.randomUUID().toString())
                        .setDomain(domain)
                        .build());
    }

    public RemoveEmployeeCommand removeEmployeeCommand(String employeeId, String domain) {
        return new RemoveEmployeeCommand(new RemoveEmployeeCommandPayload(employeeId),
                new CommandHeader.Builder()
                        .setId(employeeId)
                        .setDomain(domain)
                        .build());
    }

    public NewOrganizationCommand newOrganizationCommand(final Map<String, String> input) {
        final String name = input.get("name");
        final String domain = input.get("domain");
        final String timestamp = input.get("timestamp");
        return new NewOrganizationCommand(name, domain, timestamp);
    }
}
