package io.sytac.resumator.store;

import io.sytac.resumator.model.Employee;
import io.sytac.resumator.model.EmployeeId;

import java.util.Optional;
import java.util.Set;

/**
 * Provide a registry to the Employee entities
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public interface EmployeeRepository {

    /**
     * Retrieve one employee given its id
     *
     * @param id The id of the employee to find
     * @return The employee with a matching id, if found
     */
    Optional<Employee> find(final EmployeeId id);

    /**
     * Retrieve all known employees
     * TODO: paging
     *
     * @return All known employees
     */
    Set<Employee> findAll();

    /**
     * Store the employee. If the employee has an unknown id, it is added to the store, otherwise it will update the
     * existing record.
     *
     * @param employee The employee to store
     * @return The ID of the stored employee
     */
    EmployeeId store(final Employee employee);
}
