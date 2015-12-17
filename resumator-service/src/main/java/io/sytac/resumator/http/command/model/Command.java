package io.sytac.resumator.http.command.model;

/**
 * Describes a command that alters the state of the system
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public interface Command<H extends CommandHeader, P extends CommandPayload> {

    /**
     * Returns the {@link CommandHeader} of the command
     *
     * @return The header of the command
     */
    H getHeader();

    /**
     * Returns the {@link CommandPayload} of the command
     *
     * @return The payload of the command
     */
    P getPayload();

    /**
     * String representation of the type of the command. This is used to allow multiple Command classes to produce
     * the same kind of {@link io.sytac.resumator.model.Event}s
     *
     * @return The type of the command
     */
    String getType();
}