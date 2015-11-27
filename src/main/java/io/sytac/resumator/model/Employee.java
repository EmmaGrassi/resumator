package io.sytac.resumator.model;

import io.sytac.resumator.model.enums.Nationality;

/**
 * One employee
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Employee {
	
	private final String name;
	private final String surname;
	private final Integer yearOfBirth;
	private final Nationality nationality;
	private final String currentResidence;

	public Employee(String name, String surname, Integer yearOfBirth, Nationality nationality, String currentResidence) {
		this.name = name;
		this.surname = surname;
		this.yearOfBirth = yearOfBirth;
		this.nationality = nationality;
		this.currentResidence = currentResidence;
	}

	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public Integer getYearOfBirth() {
		return yearOfBirth;
	}
	public Nationality getNationality() {
		return nationality;
	}
	public String getCurrentResidence() {
		return currentResidence;
	}
}
