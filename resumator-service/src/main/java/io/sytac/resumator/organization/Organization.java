package io.sytac.resumator.organization;

import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.EmployeeId;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeCommandPayload;
import io.sytac.resumator.model.enums.Nationality;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Models a company, or organization
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Organization {

    private final String id;
    private final String name;
    private final String domain;
    private final ConcurrentHashMap<EmployeeId, Employee> employees = new ConcurrentHashMap<>();

    public Organization(final String id, final String name, final String domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    public Organization(final String name, final String domain) {
        this(UUID.randomUUID().toString(), name, domain);
    }

    public Employee addEmployee(final NewEmployeeCommand command) {
        final Employee employee = fromCommand(command);
        final Employee previous = employees.putIfAbsent(employee.getId(), employee);
        if(previous != null) {
            throw new IllegalArgumentException("Duplicate employee:" + employee);
        }

        return employee;
    }

    private Employee fromCommand(final NewEmployeeCommand command) {
        final NewEmployeeCommandPayload payload = command.getPayload();

        return new Employee(payload.getName(),
                payload.getSurname(),
                Integer.parseInt(payload.getYearOfBirth()),
                Nationality.valueOf(payload.getNationality()),
                payload.getCurrentResidence());

    }

    public String getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }

    public String getName() {
        return name;
    }

    public Optional<Employee> findEmployeeByName(String name, String surname) {
        return employees.values()
                        .stream()
                        .filter(employee -> name.equals(employee.getName()) &&
                                            surname.equals(employee.getSurname()))
                        .findFirst();
    }
}
