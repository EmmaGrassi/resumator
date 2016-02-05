package io.sytac.resumator.model;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * A course / training followed by the employee
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @since 0.1
 */
@Getter
@AllArgsConstructor
@ToString
public class Course {

    @NotBlank(message = "Course name is mandatory")
    private final String name;
    @NotBlank(message = "Course description is mandatory")
    private final String description;
    @Digits(message = "course year should consist of digits", fraction = 0, integer = 4)
    private final int year;

    @JsonCreator
    public Course(@JsonProperty("name") String name,
                  @JsonProperty("description") String description,
                  @JsonProperty("year") String year) {
        this(name, description, Integer.valueOf(year));
    }
}
