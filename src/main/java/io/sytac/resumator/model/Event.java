package io.sytac.resumator.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Models events flowing through the system
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Event {

    final String id;
    final String streamId;
    final Long insertOrder;
    final Long streamOrder;
    final byte[] payload;
    final Timestamp created;
    final String type;

    public Event(String id, String streamId, Long insertOrder, Long streamOrder, byte[] payload, Timestamp created, String type) {
        this.id = id;
        this.streamId = streamId;
        this.insertOrder = insertOrder;
        this.streamOrder = streamOrder;
        this.payload = payload;
        this.created = created;
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public boolean hasInsertOrder() {
        return insertOrder != null;
    }

    public Long getInsertOrder() {
        return insertOrder;
    }

    public boolean hasStreamOrder() {
        return streamOrder != null;
    }

    public Long getStreamOrder() {
        return streamOrder;
    }

    public String getStreamId() {
        return streamId;
    }
}
