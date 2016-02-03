package io.sytac.resumator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

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

    private final Degree degree;
    private final String fieldOfStudy;
    private final String school;
    private final String city;
    private final String country;
    private final int startYear;
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
