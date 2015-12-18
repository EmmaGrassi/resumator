package io.sytac.resumator.employee;

import io.sytac.resumator.model.Event;

import java.sql.Timestamp;

/**
 * Notifies of a new employee creation
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class NewEmployeeEvent extends Event<NewEmployeeCommand> {
    public NewEmployeeEvent(String id, String streamId, Long insertOrder, Long streamOrder, NewEmployeeCommand command) {
        super(id, streamId, insertOrder, streamOrder, command, asTimestamp(command), command.getType());
    }

    public NewEmployeeEvent(String id, String streamId, NewEmployeeCommand command) {
        super(id, streamId, Event.ORDER_UNSET, Event.ORDER_UNSET, command, asTimestamp(command), command.getType());
    }

    private static Timestamp asTimestamp(NewEmployeeCommand command) {
        return new Timestamp(command.getHeader().getTimestamp().getTime());
    }
}
