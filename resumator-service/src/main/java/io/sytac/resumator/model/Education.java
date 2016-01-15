package io.sytac.resumator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sytac.resumator.model.enums.Degree;

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
	private final String university;
	private final Boolean graduated;
	private final Integer graduationYear;

	@JsonCreator
	public Education(@JsonProperty("degree") Degree degree,
					 @JsonProperty("fieldOfStudy") String fieldOfStudy,
					 @JsonProperty("university") String university,
					 @JsonProperty("graduated") Boolean graduated,
					 @JsonProperty("graduationYear") Integer graduationYear) {
		this.degree = degree;
		this.fieldOfStudy = fieldOfStudy;
		this.university = university;
		this.graduated = graduated;
		this.graduationYear = graduationYear;
	}

	public String getUniversity() {
		return university;
	}
	public Degree getDegree() {
		return degree;
	}
	public String getFieldOfStudy() {
		return fieldOfStudy;
	}
	public int getGraduationYear() {
		return graduationYear;
	}
	public boolean isGraduated() {
		return graduated;
	}

}
