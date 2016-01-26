package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.model.Event;

import java.io.IOException;

/**
 * Interface for event executors
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public interface EventExecutor {
    void execute(Event event) throws IOException;
}