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
        final NewEmployeeCommand newEmployeeCommand = new NewEmployeeCommand(formParams, organizationId);
        events.publish(newEmployeeCommand);
        return newEmployeeCommand;
    }
}
