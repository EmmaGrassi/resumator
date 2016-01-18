package io.sytac.resumator.model;

/**
 * A school or university attended by the employee
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Education {

	private final Degree degree;
	private final String fieldOfStudy;
	private final String school;
    private final String city;
    private final String country;
	private final int startYear;
	private final int endYear;

    public Education(Degree degree, String fieldOfStudy, String school, String city, String country, int startYear, int endYear) {
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.school = school;
        this.city = city;
        this.country = country;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public String getSchool() {
		return school;
	}

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Degree getDegree() {
		return degree;
	}
	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
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
