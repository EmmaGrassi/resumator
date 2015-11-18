package com.sytac.resumator.model;

import java.util.List;
import java.util.Map;

import com.sytac.resumator.model.enums.LangSkill;
import com.sytac.resumator.model.enums.Language;

public class Resume {

	private String jobTitle;
	private Employee employee;
	private String shortBio;
	private Map<Language,LangSkill> languages;
	private List<Course> courses;
	private List<Education> educations;
	private List<Experience> experiences;
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String getShortBio() {
		return shortBio;
	}
	public void setShortBio(String shortBio) {
		this.shortBio = shortBio;
	}
	public Map<Language, LangSkill> getLanguages() {
		return languages;
	}
	public void setLanguages(Map<Language, LangSkill> languages) {
		this.languages = languages;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public List<Education> getEducations() {
		return educations;
	}
	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}
	public List<Experience> getExperiences() {
		return experiences;
	}
	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String title) {
		this.jobTitle = title;
	}
	
}
