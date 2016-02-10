package io.sytac.resumator.organization;

import io.sytac.resumator.command.Command;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.EmployeeCommandPayload;
import io.sytac.resumator.employee.*;
import io.sytac.resumator.employee.RemoveEmployeeCommand;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.user.Profile;
import io.sytac.resumator.user.ProfileCommandPayload;
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

    /**
     * Collection of employees of the current organisation
     */
    @Getter(AccessLevel.PRIVATE)
    private Map<String, Employee> employees = new ConcurrentHashMap<>();

    /**
     * Relations employeeId <=> employeeEmail
     */
    @Getter(AccessLevel.PRIVATE)
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
        final String employeeId = Optional.ofNullable(command.getHeader().getId()).orElse(UUID.randomUUID().toString());
        final Employee employee = fromCommand(employeeId, command);
        final Employee previous = employees.putIfAbsent(employee.getProfile().getEmail(), employee);

        if (previous != null) {
            throw new IllegalArgumentException("Duplicate employee:" + employee);
        }

        employeeIdToEmail.put(employeeId, employee.getProfile().getEmail());
        return employee;
    }

    public Employee updateEmployee(final UpdateEmployeeCommand command) {
        final String employeeId = Optional.ofNullable(command.getHeader().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot update employee because employeeId is null or empty"));

        final String employeeEmail = employeeIdToEmail.get(employeeId);
        if (!employees.containsKey(employeeEmail)) {
            throw new IllegalArgumentException(String.format("Employee with email '%s' not found in the repository", employeeEmail));
        }

        return employees.put(employeeEmail, fromCommand(employeeId, command));
    }

    public void removeEmployee(final RemoveEmployeeCommand command) {
        final String employeeId = Optional.ofNullable(command.getHeader().getId())
                .orElseThrow(() -> new IllegalArgumentException("Failed to remove employee because employeeId is null or empty"));

        final String employeeEmail = employeeIdToEmail.get(employeeId);
        if (!employees.containsKey(employeeEmail)) {
            throw new IllegalArgumentException(String.format("Employee with email '%s' does not exist", employeeEmail));
        }

        employees.remove(employeeEmail);
    }

    private Profile profileFromCommand(final String profileId, final Command command) {
        final ProfileCommandPayload payload = (ProfileCommandPayload)command.getPayload();

        return Profile.builder()
                .title(payload.getTitle())
                .name(payload.getName())
                .surname(payload.getSurname())
                .dateOfBirth(DateUtils.convert(payload.getDateOfBirth()))
                .email(payload.getEmail())
                .phoneNumber(payload.getPhonenumber())
                .nationality(Nationality.valueOf(payload.getNationality()))
                .cityOfResidence(payload.getCityOfResidence())
                .countryOfResidence(payload.getCountryOfResidence())
                .gitHub(payload.getGithub())
                .linkedIn(payload.getLinkedin())
                .aboutMe(payload.getAboutMe())
                .admin(payload.isAdmin())
                .build();
    }

    private Employee fromCommand(final String employeeId, final Command command) {
        final EmployeeCommandPayload payload = (EmployeeCommandPayload)command.getPayload();
        final EmployeeType employeeType = Optional.ofNullable(payload.getType())
                .orElse(determineEmployeeType(payload.getProfile().getEmail()));
        
        final Profile profile = Profile.builder()
                .title(payload.getProfile().getTitle())
                .name(payload.getProfile().getName())
                .surname(payload.getProfile().getSurname())
                .dateOfBirth(DateUtils.convert(payload.getProfile().getDateOfBirth()))
                .email(payload.getProfile().getEmail())
                .phoneNumber(payload.getProfile().getPhonenumber())
                .nationality(Nationality.valueOf(payload.getProfile().getNationality()))
                .cityOfResidence(payload.getProfile().getCityOfResidence())
                .countryOfResidence(payload.getProfile().getCountryOfResidence())
                .gitHub(payload.getProfile().getGithub())
                .linkedIn(payload.getProfile().getLinkedin())
                .aboutMe(payload.getProfile().getAboutMe())
                .admin(payload.getProfile().isAdmin())
                .build();
        
        return Employee.builder()
                .id(employeeId)
                .type(employeeType)
                .profile(profile)
                .educations(payload.getEducation())
                .courses(payload.getCourses())
                .experiences(payload.getExperience())
                .languages(payload.getLanguages())
                .build();
    }

    public Employee getEmployeeByEmail(final String email) {
        return employees.get(email);
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(new ArrayList<>(employees.values()));
    }

    public Optional<Employee> findEmployeeByName(final String name, final String surname) {
        return employees.values()
                        .stream()
                        .filter(employee -> name.equals(employee.getProfile().getName()) 
                                && surname.equals(employee.getProfile().getSurname()))
                        .findFirst();
    }

    private EmployeeType determineEmployeeType(final String email) {
        if (email.endsWith(domain)) {
            return EmployeeType.EMPLOYEE;
        } else {
            return EmployeeType.PROSPECT;
        }
    }
}
