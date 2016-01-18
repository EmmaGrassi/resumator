package io.sytac.resumator.command;

import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeCommandPayload;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.organization.NewOrganizationCommand;

import java.util.Date;
import java.util.Map;

/**
 * Creates command descriptors and publishes the related event
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class CommandFactory {

    public NewEmployeeCommand newEmployeeCommand(final NewEmployeeCommandPayload payload, String domain) {
        final String timestamp = String.valueOf(new Date().getTime());
        return new NewEmployeeCommand(payload, domain, timestamp);
    }

    public NewOrganizationCommand newOrganizationCommand(final Map<String, String> input) {
        final String name = input.get("name");
        final String domain = input.get("domain");
        final String timestamp = input.get("timestamp");
        return new NewOrganizationCommand(name, domain, timestamp);
    }
}
