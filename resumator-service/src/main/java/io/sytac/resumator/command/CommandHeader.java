package io.sytac.resumator.command;

import java.util.Date;
import java.util.Optional;

/**
 * Holds the metadata of a command
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class CommandHeader {
    private final Optional<String> id;
    private final Date timestamp;
    private final Optional<Long> insertOrder;
    private final Optional<Long> streamOrder;

    public CommandHeader() {
        this.timestamp = new Date();
        insertOrder = Optional.empty();
        streamOrder = Optional.empty();
        id = Optional.empty();
    }

    public CommandHeader(final Date date) {
        this.timestamp = date;
        insertOrder = Optional.empty();
        streamOrder = Optional.empty();
        id = Optional.empty();
    }

    public CommandHeader(final String id, final Date date, final Long insertOrder, final Long streamOrder) {
        this.id = Optional.ofNullable(id);
        this.timestamp = date;
        this.insertOrder = Optional.ofNullable(insertOrder);
        this.streamOrder = Optional.ofNullable(streamOrder);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Optional<Long> getInsertOrder() {
        return insertOrder;
    }

    public Optional<Long> getStreamOrder() {
        return streamOrder;
    }

    public Optional<String> getId() {
        return id;
    }
}
