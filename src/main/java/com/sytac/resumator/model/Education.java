package com.sytac.resumator.model;

import com.sytac.resumator.model.enums.Degree;

public class Education {

	private Degree degree;
	private String fieldOfStudy;
	private String school;
	private boolean graduated;
	private Integer graduationYear;
	
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public Degree getDegree() {
		return degree;
	}
	public void setDegree(Degree degree) {
		this.degree = degree;
	}
	public String getFieldOfStudy() {
		return fieldOfStudy;
	}
	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}
	public int getGraduationYear() {
		return graduationYear;
	}
	public void setGraduationYear(int graduationYear) {
		this.graduationYear = graduationYear;
	}
	public boolean isGraduated() {
		return graduated;
	}
	public void setGraduated(boolean graduated) {
		this.graduated = graduated;
	}
	
}
