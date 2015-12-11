package io.sytac.resumator.model;

import io.sytac.resumator.http.command.model.NewEmployeeCommand;
import io.sytac.resumator.http.command.model.NewEmployeeCommandPayload;
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

    public Organization(String id, String name, String domain) {
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
