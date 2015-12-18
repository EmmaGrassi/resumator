package io.sytac.resumator.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sytac.resumator.command.CommandPayload;

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

    @JsonCreator
    /* package */ NewEmployeeCommandPayload(@JsonProperty("organizationId") final String organizationId,
                                            @JsonProperty("name") final String name,
                                            @JsonProperty("surname") final String surname,
                                            @JsonProperty("yearOfBirth") final String yearOfBirth,
                                            @JsonProperty("nationality") final String nationality,
                                            @JsonProperty("currentResidence") final String currentResidence) {
        this.organizationId = organizationId;
        this.name = name;
        this.surname = surname;
        this.yearOfBirth = yearOfBirth;
        this.nationality = nationality;
        this.currentResidence = currentResidence;
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
