package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.employee.UpdateEmployeeCommand;
import io.sytac.resumator.model.Event;

import java.io.IOException;

/**
 * Executes 'UpdateEmployee' events
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class UpdateEmployeeEventExecutor extends AbstractEventExecutor {

    public UpdateEmployeeEventExecutor(final ReplayEventConfig config) {
        super(config);
    }

    @Override
    public void execute(Event event) throws IOException {
        final UpdateEmployeeCommand command = objectMapper.readValue(event.getPayload(), UpdateEmployeeCommand.class);
        checkIdAndDomainInHeader(command);
        getOrganization(command).updateEmployee(command);
    }
}