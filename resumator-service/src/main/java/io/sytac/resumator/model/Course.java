package io.sytac.resumator.model;

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

    private final String name;
    private final String description;
    private final int year;

    @JsonCreator
    public Course(@JsonProperty("name") String name,
                  @JsonProperty("description") String description,
                  @JsonProperty("year") String year) {
        this(name, description, Integer.valueOf(year));
    }
}
