package io.sytac.resumator.organization;

import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeCommandPayload;
import io.sytac.resumator.employee.RemoveEmployeeCommand;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.utils.DateUtils;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Models a company, or organization
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Getter
public class Organization {

    private final String id;
    private final String name;
    private final String domain;

    @Getter(AccessLevel.NONE)
    private Map<String, Employee> employees = new ConcurrentHashMap<>();

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

    public void removeEmployee(final RemoveEmployeeCommand command) {
        final String employeeId = command.getHeader().getId()
                .orElseThrow(() -> new IllegalArgumentException("Failed to remove employee because employeeId is null or empty"));

        if (employees.containsKey(employeeId)) {
            employees.remove(employeeId);
        } else {
            throw new IllegalArgumentException(String.format("Employee with id '%s' does not exist", employeeId));
        }
    }

    private Employee fromCommand(final NewEmployeeCommand command) {
        final NewEmployeeCommandPayload payload = command.getPayload();
        final String employeeId = command.getHeader().getId().orElse(UUID.randomUUID().toString());

        return new Employee(employeeId,
                payload.getTitle(),
                payload.getName(),
                payload.getSurname(),
                payload.getEmail(),
                payload.getPhonenumber(),
                payload.getGithub(),
                payload.getLinkedin(),
                DateUtils.convert(payload.getDateOfBirth()),
                Nationality.valueOf(payload.getNationality()),
                payload.getCurrentResidence(),
                payload.getAboutMe(),
                payload.getEducation(),
                payload.getCourses(),
                payload.getExperience(),
                payload.getLanguages());

    }

    public Employee getEmployeeById(String id) {
        return employees.get(id);
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(new ArrayList<>(employees.values()));
    }

    public Optional<Employee> findEmployeeByName(String name, String surname) {
        return employees.values()
                        .stream()
                        .filter(employee -> name.equals(employee.getName()) && surname.equals(employee.getSurname()))
                        .findFirst();
    }
}
