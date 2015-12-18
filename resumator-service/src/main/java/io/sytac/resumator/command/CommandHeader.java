package io.sytac.resumator.command;

import java.util.Date;

/**
 * Holds the metadata of a command
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class CommandHeader {
    private final Date timestamp;

    public CommandHeader() {
        this.timestamp = new Date();
    }

    public CommandHeader(final Date date) {
        this.timestamp = date;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
