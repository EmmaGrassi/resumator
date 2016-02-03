package io.sytac.resumator.employee;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.sytac.resumator.command.CommandPayload;
import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.Experience;
import io.sytac.resumator.model.Language;
import lombok.Getter;

/**
 * Defines the payload of a new Employee command
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Getter
public class EmployeeCommandPayload implements CommandPayload {

    @NotBlank(message = "Title is mandatory")
    private final String title;
    @NotBlank(message = "name is mandatory")
    private final String name;
    @NotBlank(message = "surname is mandatory")
    private final String surname;
    @NotBlank(message = "email is mandatory")
    @Email(message = "email format is not correct")
    private final String email;
    @NotBlank(message = "phonenumber is mandatory")
    @Digits(message = "phonenumber should consist of digits", fraction = 0, integer = 15)
    private final String phonenumber;
    private final String github;
    private final String linkedin;
    @NotBlank(message = "dateOfBirth is mandatory")
    private final String dateOfBirth;
    @NotBlank(message = "nationality is mandatory")
    private final String nationality;
    @NotBlank(message = "currentResidence is mandatory")
    private final String currentResidence;
    @NotBlank(message = "aboutMe is mandatory")
    private final String aboutMe;
    @Valid
    private final List<Education> education;
    @Valid
    private final List<Course> courses;
    @Valid
    private final List<Experience> experience;
    @Valid
    private final List<Language> languages;
    private final boolean admin;

    @JsonCreator
    public EmployeeCommandPayload(@JsonProperty("title") final String title,
                                  @JsonProperty("name") final String name,
                                  @JsonProperty("surname") final String surname,
                                  @JsonProperty("email") final String email,
                                  @JsonProperty("phonenumber") final String phonenumber,
                                  @JsonProperty("github") final String github,
                                  @JsonProperty("linkedin") final String linkedin,
                                  @JsonProperty("dateOfBirth") final String dateOfBirth,
                                  @JsonProperty("nationality") final String nationality,
                                  @JsonProperty("currentResidence") final String currentResidence,
                                  @JsonProperty("aboutMe") final String aboutMe,
                                  @JsonProperty("education") final List<Education> education,
                                  @JsonProperty("courses") final List<Course> courses,
                                  @JsonProperty("experience") final List<Experience> experience,
                                  @JsonProperty("languages") final List<Language> languages,
                                  @JsonProperty("admin") final boolean admin) {

        this.title = title;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.github = github;
        this.linkedin = linkedin;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.currentResidence = currentResidence;
        this.aboutMe = aboutMe;
        this.education = education;
        this.courses = courses;
        this.experience = experience;
        this.languages = languages;
        this.admin = admin;
    }
}
