package io.sytac.resumator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sytac.resumator.utils.DateUtils;

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
	private final List<String> technologies;
	private final List<String> methodologies;
	private final Long startDate;
	private final Long endDate;

	public Experience(String companyName, String title, String location, String shortDescription,
					  List<String> technologies, List<String> methodologies, Long startDate, Long endDate) {
		this.companyName = companyName;
		this.title = title;
		this.location = location;
		this.shortDescription = shortDescription;
		this.technologies = technologies;
		this.methodologies = methodologies;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@JsonCreator
	public Experience(@JsonProperty("companyName") String companyName,
					  @JsonProperty("title") String title,
					  @JsonProperty("location") String location,
					  @JsonProperty("shortDescription") String shortDescription,
					  @JsonProperty("technologies") List<String> technologies,
					  @JsonProperty("methodologies") List<String> methodologies,
					  @JsonProperty("startDate") String startDate,
					  @JsonProperty("endDate") String endDate) {
		this.companyName = companyName;
		this.title = title;
		this.location = location;
		this.shortDescription = shortDescription;
		this.technologies = technologies;
		this.methodologies = methodologies;
		this.startDate = DateUtils.convert(startDate);
		this.endDate = DateUtils.convert(endDate);
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
	public Long getStartDate() {
		return startDate;
	}
	public Long getEndDate() {
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
