package io.sytac.resumator;

import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;

import javax.inject.Inject;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Create the factory for commands
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Provider
public class CommandFactoryResolver implements ContextResolver<CommandFactory> {

    private final EventPublisher events;

    @Inject
    public CommandFactoryResolver(final EventPublisher publisher) {
        this.events = publisher;
    }

    @Override
    public CommandFactory getContext(Class<?> type) {
        return new CommandFactory(events);
    }
}
