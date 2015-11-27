package com.sytac.resumator.model;

import java.util.Date;

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
	private final Date date;

	public Course(String name, String description, Date date) {
		this.name = name;
		this.description = description;
		this.date = date;
	}

	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Date getYear() {
		return date;
	}

}
