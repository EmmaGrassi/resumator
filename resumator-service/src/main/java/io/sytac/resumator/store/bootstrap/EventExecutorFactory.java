package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.RemoveEmployeeCommand;
import io.sytac.resumator.employee.UpdateEmployeeCommand;
import io.sytac.resumator.organization.NewOrganizationCommand;
import io.sytac.resumator.user.NewProfileCommand;
import io.sytac.resumator.user.RemoveProfileCommand;
import io.sytac.resumator.user.UpdateProfileCommand;

/**
 * Event executor factory provides new instances of {@link EventExecutor} or
 * throws {@link IllegalStateException} if event type is unknown
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class EventExecutorFactory {

    public static EventExecutor getInstance(final String eventType, final ReplayEventConfig config) {
        if (NewProfileCommand.EVENT_TYPE.equals(eventType)) {
            return new NewProfileEventExecutor(config);
        }
        if (UpdateProfileCommand.EVENT_TYPE.equals(eventType)) {
            return new UpdateProfileEventExecutor(config);
        }
        if (RemoveProfileCommand.EVENT_TYPE.equals(eventType)) {
            return new RemoveProfileEventExecutor(config);
        }

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

        throw new IllegalStateException("Could not format the following unknown event type: " + eventType);
    }
}