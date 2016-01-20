package io.sytac.resumator.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
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
@Getter
@AllArgsConstructor
@ToString
public class Event {

    public static final Long ORDER_UNSET = null;

    final String id;
    final Long insertOrder;
    final String payload;

    @Getter(AccessLevel.NONE)
    final Timestamp created;

    final String type;

    public Event(String id, Long insertOrder, Clob payload, Timestamp created, String type) {
        this(id, insertOrder, readClob(payload), created, type);
    }

    private static String readClob(Clob clob) {
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

    public boolean hasInsertOrder() {
        return insertOrder != null;
    }
}
