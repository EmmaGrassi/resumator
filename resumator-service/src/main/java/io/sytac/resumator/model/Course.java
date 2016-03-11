package io.sytac.resumator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * A course / training followed by the employee
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @since 0.1
 */
@Getter
@ToString
public class Course {

    @NotBlank(message = "Course name is mandatory")
    private final String name;    
    @NotBlank(message = "Course description is mandatory")
    private final String description;
    @NotNull
    @Range(min = 1960, max = 2030 ,message= "Course year should consist of digits")
    private final int year;

    @JsonCreator
    public Course(@JsonProperty("name") String name,
                  @JsonProperty("description") String description,
                  @JsonProperty("year") int year) {
       this.name=name;
       this.description=description;
       this.year=year;
    }
}
