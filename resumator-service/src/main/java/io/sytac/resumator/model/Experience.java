package io.sytac.resumator.model;

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
	private final String city;
	private final String country;
	private final String shortDescription;
	private final List<String> technologies;
	private final List<String> methodologies;
	private final Date startDate;
	private final Date endDate;

	public Experience(String companyName, String title, String city, String country, String shortDescription, List<String> technologies, List<String> methodologies, Date startDate, Date endDate) {
		this.companyName = companyName;
		this.title = title;
		this.city = city;
		this.country = country;
		this.shortDescription = shortDescription;
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
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public List<String> getTechnologies() {
		return technologies;
	}
	public List<String> getMethodologies() {
		return methodologies;
	}
}
