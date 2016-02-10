package io.sytac.resumator.employee;

import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.Experience;
import io.sytac.resumator.model.Language;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.user.Profile;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * One employee. Acts as the Aggregate Root for the Employee aggregate
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Getter
@EqualsAndHashCode
@ToString
public class Employee {

    private final String id;
    private final EmployeeType type;
    private final Profile profile;
    private final List<Education> educations;
    private final List<Course> courses;
    private final List<Experience> experiences;
    private final List<Language> languages;

    @Builder
    Employee(String id, EmployeeType type, Profile profile, List<Education> educations, List<Course> courses,
             List<Experience> experiences, List<Language> languages) {

        this.id = id == null ? UUID.randomUUID().toString() : id;
        this.type = type;
        this.profile = profile;
        this.educations = educations;
        this.courses = courses;
        this.experiences = experiences;
        this.languages = languages;
    }
}
