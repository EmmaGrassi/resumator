package com.sytac.resumator.model;

import java.util.Date;
import java.util.List;

public class Experience {
	
	private String companyName;
	private String title;
	private String location;
	private String shortDescription;
	private List<String> roles;
	private List<String> technologies;
	private List<String> methodologies;
	private Date startDate;
	private Date endDate;

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String company) {
		this.companyName = company;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<String> getTechnologies() {
		return technologies;
	}
	public void setTechnologies(List<String> technologies) {
		this.technologies = technologies;
	}
	public List<String> getMethodologies() {
		return methodologies;
	}
	public void setMethodologies(List<String> methodologies) {
		this.methodologies = methodologies;
	}
	

	
}
