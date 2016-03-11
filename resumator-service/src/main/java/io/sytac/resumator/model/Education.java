package io.sytac.resumator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * A school or university attended by the employee
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @since 0.1
 */
@Getter
@ToString
public class Education {

    @NotNull(message = "degree is mandatory")
    private final Degree degree;
    @NotBlank(message = "fieldOfStudy is mandatory")
    private final String fieldOfStudy;
    @NotBlank(message = "school is mandatory")
    private final String school;
    @NotBlank(message = "city is mandatory")
    private final String city;
    @NotBlank(message = "country is mandatory")
    private final String country;
    @NotNull
    @Range(min = 1960, max = 2030 ,message= "Education startYear should consist of 4 digits")
    private final int startYear;
    @NotNull
    @Range(min = 1960, max = 2030,message= "Education  endYear should consist of 4 digits")
    private final int endYear;

    @JsonCreator
    public Education(@JsonProperty("degree") Degree degree,
                     @JsonProperty("fieldOfStudy") String fieldOfStudy,
                     @JsonProperty("school") String school,
                     @JsonProperty("city") String city,
                     @JsonProperty("country") String country,
                     @JsonProperty("startYear") int startYear,
                     @JsonProperty("endYear") int endYear) {
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.school = school;
        this.city = city;
        this.country = country;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public enum Degree {
        ASSOCIATE_DEGREE("Associate Degree"),
        BACHELOR_DEGREE("Bachelor Degree"),
        MASTER_DEGREE("Master Degree"),
        ENGINEER_DEGREE("Engineer Degree"),
        DOCTORAL("Doctoral"),
        OTHER("Other");

        private final String asText;

        Degree(String asText) {
            this.asText = asText;
        }

        public String asText() {
            return asText;
        }
    }
}
