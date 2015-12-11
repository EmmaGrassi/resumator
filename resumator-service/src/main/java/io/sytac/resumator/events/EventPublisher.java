package io.sytac.resumator.events;

import io.sytac.resumator.http.command.model.NewEmployeeCommand;
import io.sytac.resumator.model.Event;

/**
 * Publish domain events within the system
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public interface EventPublisher {

    /**
     * A new employee was added to the system
     *
     * @param command The command describing the added employee
     * @return The {@link Event} that was broadcasted
     */
    Event publish(NewEmployeeCommand command);
}
