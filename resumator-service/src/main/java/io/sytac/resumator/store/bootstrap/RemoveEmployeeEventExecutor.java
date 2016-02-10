package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.employee.RemoveEmployeeCommand;
import io.sytac.resumator.model.Event;

import java.io.IOException;

/**
 * Executes 'RemoveEmployee' events
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class RemoveEmployeeEventExecutor extends AbstractEventExecutor {

    public RemoveEmployeeEventExecutor(final ReplayEventConfig config) {
        super(config);
    }

    @Override
    public void execute(Event event) throws IOException {
        final RemoveEmployeeCommand command = config.getObjectMapper().readValue(event.getPayload(), RemoveEmployeeCommand.class);
        checkIdInHeader(command);
        checkDomainInHeader(command);
        getOrganization(command).removeEmployee(command);
    }
}