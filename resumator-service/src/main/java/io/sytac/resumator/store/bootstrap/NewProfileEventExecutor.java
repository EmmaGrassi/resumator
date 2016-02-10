package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.user.NewProfileCommand;

import java.io.IOException;

/**
 * Executes 'NewEmployee' events
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class NewProfileEventExecutor extends AbstractEventExecutor {

    public NewProfileEventExecutor(final ReplayEventConfig config) {
        super(config);
    }

    @Override
    public void execute(Event event) throws IOException {
        final NewProfileCommand command = config.getObjectMapper().readValue(event.getPayload(), NewProfileCommand.class);
        checkIdInHeader(command);
        getProfileRepository().add(command);
    }
}