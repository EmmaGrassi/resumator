package io.sytac.resumator.model;

import io.sytac.resumator.model.enums.LangSkill;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	private final Map<Locale,LangSkill> languages;
	private final List<Course> courses;
	private final List<Education> educations;
	private final List<Experience> experiences;

	public Resume(String jobTitle, Employee employee, String shortBio, Map<Locale, LangSkill> languages, List<Course> courses, List<Education> educations, List<Experience> experiences) {
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
	public Map<Locale, LangSkill> getLanguages() {
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
