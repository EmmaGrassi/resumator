package io.sytac.resumator.organization;

import io.sytac.resumator.command.Command;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.EmployeeCommandPayload;
import io.sytac.resumator.employee.*;
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

    @Getter(AccessLevel.NONE)
    private Map<String, String> employeeIdToEmail = new ConcurrentHashMap<>();


    public Organization(final String id, final String name, final String domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    public Organization(final String name, final String domain) {
        this(UUID.randomUUID().toString(), name, domain);
    }

    public Employee addEmployee(final NewEmployeeCommand command) {
        final String employeeId = command.getHeader().getId().orElse(UUID.randomUUID().toString());
        final Employee employee = fromCommand(employeeId, command);
        final Employee previous = employees.putIfAbsent(employee.getEmail(), employee);

        if (previous != null) {
            throw new IllegalArgumentException("Duplicate employee:" + employee);
        }

        employeeIdToEmail.put(employeeId, employee.getEmail());
        return employee;
    }

    public Employee updateEmployee(final UpdateEmployeeCommand command) {
        final String employeeId = command.getHeader().getId()
                .orElseThrow(() -> new IllegalArgumentException("Cannot update employee because employeeId is null or empty"));

        final String employeeEmail = employeeIdToEmail.get(employeeId);
        if (!employees.containsKey(employeeEmail)) {
            throw new IllegalArgumentException(String.format("Employee with email '%s' not found in the repository", employeeEmail));
        }

        return employees.put(employeeEmail, fromCommand(employeeId, command));
    }

    public void removeEmployee(final RemoveEmployeeCommand command) {
        final String employeeId = command.getHeader().getId()
                .orElseThrow(() -> new IllegalArgumentException("Failed to remove employee because employeeId is null or empty"));

        final String employeeEmail = employeeIdToEmail.get(employeeId);
        if (!employees.containsKey(employeeEmail)) {
            throw new IllegalArgumentException(String.format("Employee with email '%s' does not exist", employeeEmail));
        }

        employees.remove(employeeEmail);
    }

    private Employee fromCommand(final String employeeId, final Command command) {
        final EmployeeCommandPayload payload = (EmployeeCommandPayload)command.getPayload();

        return Employee.builder()
                .id(employeeId)
                .title(payload.getTitle())
                .name(payload.getName())
                .surname(payload.getSurname())
                .email(payload.getEmail())
                .phoneNumber(payload.getPhonenumber())
                .gitHub(payload.getGithub())
                .linkedIn(payload.getLinkedin())
                .dateOfBirth(DateUtils.convert(payload.getDateOfBirth()))
                .nationality(Nationality.valueOf(payload.getNationality()))
                .currentResidence(payload.getCurrentResidence())
                .aboutMe(payload.getAboutMe())
                .educations(payload.getEducation())
                .courses(payload.getCourses())
                .experiences(payload.getExperience())
                .languages(payload.getLanguages())
                .admin(payload.isAdmin())
                .build();
    }

    public Employee getEmployeeByEmail(String email) {
        return employees.get(email);
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
