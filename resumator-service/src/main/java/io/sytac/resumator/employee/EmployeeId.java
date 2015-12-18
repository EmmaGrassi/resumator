package io.sytac.resumator.employee;

import java.util.UUID;

/**
 * Value Object storing the ID of an Employee aggregate root.
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class EmployeeId {

    private final UUID internalId;

    public EmployeeId() {
        internalId = UUID.randomUUID();
    }

    public EmployeeId(final String id) {
        internalId = UUID.fromString(id);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmployeeId &&
               internalId.equals(((EmployeeId)obj).internalId);
    }

    @Override
    public int hashCode() {
        return internalId.hashCode();
    }

    @Override
    public String toString() {
        return internalId.toString();
    }
}
