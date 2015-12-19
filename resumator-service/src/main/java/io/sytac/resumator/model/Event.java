package io.sytac.resumator.model;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Models events flowing through the system
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@MappedJdbcTypes(JdbcType.CLOB)
public class Event {

    public static final Long ORDER_UNSET = Long.MIN_VALUE;

    final String id;
    final String streamId;
    final Long insertOrder;
    final Long streamOrder;
    final String payload;
    final Timestamp created;
    final String type;

    public Event(String id, String streamId, Long insertOrder, Long streamOrder, String payload, Timestamp created, String type) {
        this.id = id;
        this.streamId = streamId;
        this.insertOrder = insertOrder;
        this.streamOrder = streamOrder;
        this.payload = payload;
        this.created = created;
        this.type = type;
    }
    public Event(String id, String streamId, Long insertOrder, Long streamOrder, Clob payload, Timestamp created, String type) {
        this.id = id;
        this.streamId = streamId;
        this.insertOrder = insertOrder;
        this.streamOrder = streamOrder;
        this.payload = readClob(payload);
        this.created = created;
        this.type = type;
    }

    private String readClob(Clob clob) {
        if (clob != null) {
            try {
                int size = (int) clob.length();
                return clob.getSubString(1, size);
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }

        throw new IllegalArgumentException("Null event payload");
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
        return payload;
    }
}
