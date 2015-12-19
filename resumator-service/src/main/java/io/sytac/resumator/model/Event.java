package io.sytac.resumator.model;

import java.nio.charset.Charset;
import java.sql.Timestamp;

/**
 * Models events flowing through the system
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Event {

    public static final Long ORDER_UNSET = Long.MIN_VALUE;

    final String id;
    final String streamId;
    final Long insertOrder;
    final Long streamOrder;
    final byte[] payload;
    final Timestamp created;
    final String type;

    public Event(String id, String streamId, Long insertOrder, Long streamOrder, String payload, Timestamp created, String type) {
        this.id = id;
        this.streamId = streamId;
        this.insertOrder = insertOrder;
        this.streamOrder = streamOrder;
        this.payload = payload.getBytes(Charset.forName("UTF-8"));
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

    public String getType() {
        return type;
    }

    public String getPayload() {
        return new String(payload, Charset.forName("UTF-8"));
    }
}
