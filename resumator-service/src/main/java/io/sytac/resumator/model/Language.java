package io.sytac.resumator.model;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * A language an employee speaks.
 *
 * @author Jack Tol
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Getter
@ToString
public class Language {

	@NotBlank(message = "language name is mandatory")
    private final String name;
	@NotBlank(message = "language proficiency is mandatory")
    private final Proficiency proficiency;

    @JsonCreator
    public Language(@JsonProperty("name") String name,
                    @JsonProperty("proficiency") Proficiency proficiency) {
        this.name = name;
        this.proficiency = proficiency;
    }

    public enum Proficiency {
        ELEMENTARY("Elementary"),
        LIMITED_WORKING("Limited Working Experience"),
        PROFESSIONAL_WORKING("Professional Working Experience"),
        FULL_PROFESSIONAL("Full Professional"),
        NATIVE("Native");


        private final String asText;

        Proficiency(String asText) {
            this.asText = asText;
        }

        public String asText() {
            return asText;
        }
    }
}
