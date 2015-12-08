package io.sytac.resumator.store;

import io.sytac.resumator.model.Employee;
import io.sytac.resumator.model.EmployeeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bag of employees, stored in memory
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class InMemoryEmployeeRepository implements EmployeeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryEmployeeRepository.class);

    private final ConcurrentHashMap<EmployeeId, Employee> employees = new ConcurrentHashMap<>();

    @Override
    public Optional<Employee> find(EmployeeId id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Set<Employee> findAll() {
        return Collections.unmodifiableSet(new HashSet<>(employees.values()));
    }

    @Override
    public EmployeeId store(Employee employee) {
        employees.put(employee.getId(), employee);

        return employee.getId();
    }
}
