package io.sytac.resumator.model;

import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.EmployeeId;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeCommandPayload;
import io.sytac.resumator.model.enums.Nationality;
import org.eclipse.jetty.util.ConcurrentHashSet;

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
    private final ConcurrentHashSet<EmployeeId> employees = new ConcurrentHashSet<>();

    public Organization(final String id, final String name, final String domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    public Employee addEmployee(final NewEmployeeCommand command) {
        final Employee employee = fromCommand(command);
        employees.add(employee.getId());

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
}
