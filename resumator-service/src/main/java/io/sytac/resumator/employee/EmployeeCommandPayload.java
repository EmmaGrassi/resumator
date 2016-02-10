package io.sytac.resumator.employee;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;

import io.sytac.resumator.user.ProfileCommandPayload;
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

    private final EmployeeType type;

    @Valid
    private final ProfileCommandPayload profile;

    @Valid
    private final List<Education> education;

    @Valid
    private final List<Course> courses;

    @Valid
    private final List<Experience> experience;

    @Valid
    private final List<Language> languages;


    @JsonCreator
    public EmployeeCommandPayload(@JsonProperty("type") final EmployeeType type,
                                  @JsonProperty("profile") final ProfileCommandPayload profile,
                                  @JsonProperty("education") final List<Education> education,
                                  @JsonProperty("courses") final List<Course> courses,
                                  @JsonProperty("experience") final List<Experience> experience,
                                  @JsonProperty("languages") final List<Language> languages) {
        this.type = type;
        this.profile = profile;
        this.education = education;
        this.courses = courses;
        this.experience = experience;
        this.languages = languages;
    }
}
