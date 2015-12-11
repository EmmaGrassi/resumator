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


    Event publish(NewEmployeeCommand command);
}
