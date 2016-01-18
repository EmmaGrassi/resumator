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

    public static final Long ORDER_UNSET = null;

    final String id;
    final Long insertOrder;
    final String payload;
    final Timestamp created;
    final String type;

    public Event(String id, Long insertOrder, String payload, Timestamp created, String type) {
        this.id = id;
        this.insertOrder = insertOrder;
        this.payload = payload;
        this.created = created;
        this.type = type;
    }
    public Event(String id, Long insertOrder, Clob payload, Timestamp created, String type) {
        this.id = id;
        this.insertOrder = insertOrder;
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

    public String getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", insertOrder=" + insertOrder +
                ", payload='" + payload + '\'' +
                ", created=" + created +
                ", type='" + type + '\'' +
                '}';
    }
}
