package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.model.Event;
import io.sytac.resumator.user.UpdateProfileCommand;

import java.io.IOException;

/**
 * Executes 'UpdateProfile' events
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class UpdateProfileEventExecutor extends AbstractEventExecutor {

    public UpdateProfileEventExecutor(final ReplayEventConfig config) {
        super(config);
    }

    @Override
    public void execute(Event event) throws IOException {
        final UpdateProfileCommand command = config.getObjectMapper().readValue(event.getPayload(), UpdateProfileCommand.class);
        checkIdInHeader(command);
        getProfileRepository().update(command);
    }
}