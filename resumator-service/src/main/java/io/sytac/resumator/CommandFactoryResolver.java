package io.sytac.resumator;

import io.sytac.resumator.command.CommandFactory;
import org.glassfish.hk2.api.Factory;

import javax.ws.rs.ext.Provider;

/**
 * Create the factory for commands
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Provider
public class CommandFactoryResolver implements Factory<CommandFactory> {

    @Override
    public CommandFactory provide() {
        return new CommandFactory();
    }

    @Override
    public void dispose(CommandFactory instance) {
        // NOP
    }
}
