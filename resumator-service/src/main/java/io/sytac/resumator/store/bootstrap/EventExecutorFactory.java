package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.RemoveEmployeeCommand;
import io.sytac.resumator.employee.UpdateEmployeeCommand;
import io.sytac.resumator.exception.ResumatorInternalException;
import io.sytac.resumator.organization.NewOrganizationCommand;

/**
 * Event executor factory provides new instances of {@link EventExecutor} or
 * throws {@link IllegalStateException} if event type is unknown
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class EventExecutorFactory {

    public static EventExecutor getInstance(String eventType, ReplayEventConfig config) {
        if (NewEmployeeCommand.EVENT_TYPE.equals(eventType)) {
            return new NewEmployeeEventExecutor(config);
        }
        if (UpdateEmployeeCommand.EVENT_TYPE.equals(eventType)) {
            return new UpdateEmployeeEventExecutor(config);
        }
        if (RemoveEmployeeCommand.EVENT_TYPE.equals(eventType)) {
            return new RemoveEmployeeEventExecutor(config);
        }
        if (NewOrganizationCommand.EVENT_TYPE.equals(eventType)) {
            return new NewOrganizationEventExecutor(config);
        }

        throw new ResumatorInternalException("Could not format the following unknown event type: " + eventType);
    }
}