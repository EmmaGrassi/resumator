package io.sytac.resumator.command;

import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.organization.NewOrganizationCommand;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Creates command descriptors and publishes the related event
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class CommandFactory {

    private final EventPublisher events;

    public CommandFactory(final EventPublisher eventPublisher) {
        this.events = eventPublisher;
    }

    public NewEmployeeCommand newEmployeeCommand(final Map<String, String> input, final String organizationDomain) {
        final String name = input.get("name");
        final String surname = input.get("surname");
        final String yearOfBirth = input.get("dateOfBirth");
        final String nationality = input.get("nationality");
        final String currentResident = input.get("nationality");
        final String timestamp = String.valueOf(new Date().getTime());
        final NewEmployeeCommand newEmployeeCommand = new NewEmployeeCommand(organizationDomain, name, surname, yearOfBirth, nationality, currentResident, timestamp);
        events.publish(newEmployeeCommand);
        return newEmployeeCommand;
    }

    public NewOrganizationCommand newOrganizationCommand(final Map<String, String> input) {
        final String name = input.get("name");
        final String domain = input.get("domain");
        final String timestamp = input.get("timestamp");
        final NewOrganizationCommand newOrganizationCommand = new NewOrganizationCommand(name, domain, timestamp);
        events.publish(newOrganizationCommand);
        return newOrganizationCommand;
    }
}
