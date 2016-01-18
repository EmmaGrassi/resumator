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
	private final int year;

	public Course(String name, String description, int year) {
		this.name = name;
		this.description = description;
		this.year = year;
	}

	@JsonCreator
	public Course(@JsonProperty("name") String name,
				  @JsonProperty("description") String description,
				  @JsonProperty("date") String year) {
		this.name = name;
		this.description = description;
		this.year = Integer.valueOf(year);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getYear() {
		return year;
	}
}
