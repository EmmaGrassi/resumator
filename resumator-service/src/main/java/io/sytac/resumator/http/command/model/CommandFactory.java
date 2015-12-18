package io.sytac.resumator.http.command.model;

import io.sytac.resumator.events.EventPublisher;

import javax.ws.rs.core.MultivaluedMap;

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

    public NewEmployeeCommand newEmployeeCommand(final MultivaluedMap<String, String> formParams, final String organizationId) {
        final String name = formParams.getFirst("name");
        final String surname = formParams.getFirst("name");
        final String yearOfBirth = formParams.getFirst("name");
        final String nationality = formParams.getFirst("name");
        final String currentResident = formParams.getFirst("name");
        final String timestamp = formParams.getFirst("timestamp");
        final NewEmployeeCommand newEmployeeCommand = new NewEmployeeCommand(organizationId, name, surname, yearOfBirth, nationality, currentResident, timestamp);
        events.publish(newEmployeeCommand);
        return newEmployeeCommand;
    }
}
