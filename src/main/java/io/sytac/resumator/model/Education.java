package io.sytac.resumator.model;

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
	private final String school;
	private final Boolean graduated;
	private final Integer graduationYear;

	public Education(Degree degree, String fieldOfStudy, String school, Boolean graduated, Integer graduationYear) {
		this.degree = degree;
		this.fieldOfStudy = fieldOfStudy;
		this.school = school;
		this.graduated = graduated;
		this.graduationYear = graduationYear;
	}

	public String getSchool() {
		return school;
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
