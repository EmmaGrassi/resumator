package io.sytac.resumator.model;

/**
 * A language an employee speaks.
 *
 * @author Jack Tol
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class Language {

    private final String name;
    private final Proficiency proficiency;

    @JsonCreator
    public Language(@JsonProperty("name") String name,
                    @JsonProperty("proficiency") Proficiency proficiency) {
        this.name = name;
        this.proficiency = proficiency;
    }

    public String getName() {
        return name;
    }

    public Proficiency getProficiency() {
        return proficiency;
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
