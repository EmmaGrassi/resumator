package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.model.Event;
import io.sytac.resumator.user.RemoveProfileCommand;

import java.io.IOException;

/**
 * Executes 'RemoveProfile' events
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class RemoveProfileEventExecutor extends AbstractEventExecutor {

    public RemoveProfileEventExecutor(final ReplayEventConfig config) {
        super(config);
    }

    @Override
    public void execute(Event event) throws IOException {
        final RemoveProfileCommand command = config.getObjectMapper().readValue(event.getPayload(), RemoveProfileCommand.class);
        checkIdInHeader(command);
        getProfileRepository().remove(command);
    }
}