package com.sytac.resumator.model;

import java.util.Date;
import java.util.List;

/**
 * The working experience of an employee
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Experience {
	
	private final String companyName;
	private final String title;
	private final String location;
	private final String shortDescription;
	private final List<String> roles;
	private final List<String> technologies;
	private final List<String> methodologies;
	private final Date startDate;
	private final Date endDate;

	public Experience(String companyName, String title, String location, String shortDescription, List<String> roles, List<String> technologies, List<String> methodologies, Date startDate, Date endDate) {
		this.companyName = companyName;
		this.title = title;
		this.location = location;
		this.shortDescription = shortDescription;
		this.roles = roles;
		this.technologies = technologies;
		this.methodologies = methodologies;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getCompanyName() {
		return companyName;
	}
	public String getTitle() {
		return title;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public List<String> getRoles() {
		return roles;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getLocation() {
		return location;
	}
	public List<String> getTechnologies() {
		return technologies;
	}
	public List<String> getMethodologies() {
		return methodologies;
	}


	
}
