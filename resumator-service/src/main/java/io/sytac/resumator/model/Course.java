package io.sytac.resumator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sytac.resumator.utils.DateUtils;

/**
 * A course / training followed by the employee
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Course {
	
	private final String name;
	private final String description;
	private final Long date;

	public Course(String name, String description, Long date) {
		this.name = name;
		this.description = description;
		this.year = year;
	}

	@JsonCreator
	public Course(@JsonProperty("name") String name,
				  @JsonProperty("description") String description,
				  @JsonProperty("date") String date) {
		this.name = name;
		this.description = description;
		this.date = DateUtils.convert(date);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	public Long getDate() {
		return date;
	}

	public int getYear() {
		return year;
	}
}
