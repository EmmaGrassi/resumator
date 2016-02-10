package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.model.Event;

import java.io.IOException;

/**
 * Executes 'NewEmployee' events
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class NewEmployeeEventExecutor extends AbstractEventExecutor {

    public NewEmployeeEventExecutor(final ReplayEventConfig config) {
        super(config);
    }

    @Override
    public void execute(Event event) throws IOException {
        final NewEmployeeCommand command = config.getObjectMapper().readValue(event.getPayload(), NewEmployeeCommand.class);
        checkIdInHeader(command);
        checkDomainInHeader(command);
        getOrganization(command).addEmployee(command);
    }
}