package io.sytac.resumator.http.command.model;

/**
 * Defines the payload of a new Employee command
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class NewEmployeeCommandPayload extends CommandPayload {

    private final String organizationId;
    private final String name;
    private final String surname;
    private final String yearOfBirth;
    private final String nationality;
    private final String currentResidence;

    public NewEmployeeCommandPayload(final String organizationId,
                                     final String name,
                                     final String surname,
                                     final String yearOfBirth,
                                     final String nationality,
                                     final String currentResidence) {
        this.organizationId = organizationId;
        this.name = name;
        this.surname = surname;
        this.yearOfBirth = yearOfBirth;
        this.nationality = nationality;
        this.currentResidence = currentResidence;
    }

    public NewEmployeeCommandPayload() {
        this.organizationId = null;
        this.name = null;
        this.surname = null;
        this.yearOfBirth = null;
        this.nationality = null;
        this.currentResidence = null;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public String getCurrentResidence() {
        return currentResidence;
    }
}
