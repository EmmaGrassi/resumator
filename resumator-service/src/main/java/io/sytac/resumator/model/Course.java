package io.sytac.resumator.model;

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
