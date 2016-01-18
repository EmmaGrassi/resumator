package io.sytac.resumator.model;

import io.sytac.resumator.employee.Employee;

import java.util.List;

/**
 * The complete CV of an employee
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Resume {

	private final String jobTitle;
	private final Employee employee;
	private final String shortBio;
	private final List<Language> languages;
	private final List<Course> courses;
	private final List<Education> educations;
	private final List<Experience> experiences;

	public Resume(String jobTitle, Employee employee, String shortBio, List<Language> languages, List<Course> courses, List<Education> educations, List<Experience> experiences) {
		this.jobTitle = jobTitle;
		this.employee = employee;
		this.shortBio = shortBio;
		this.languages = languages;
		this.courses = courses;
		this.educations = educations;
		this.experiences = experiences;
	}

	public Employee getEmployee() {
		return employee;
	}
	public String getShortBio() {
		return shortBio;
	}
	public List<Language> getLanguages() {
		return languages;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public List<Education> getEducations() {
		return educations;
	}
	public List<Experience> getExperiences() {
		return experiences;
	}
	public String getJobTitle() {
		return jobTitle;
	}

}
