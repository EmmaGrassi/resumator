package com.sytac.resumator.model;

import com.sytac.resumator.model.enums.Nationality;

public class Employee {
	
	private String name;
	private String surname;
	private int yearOfBirth;
	private Nationality nationality;
	private String currentResidence;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getYearOfBirth() {
		return yearOfBirth;
	}
	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
	public Nationality getNationality() {
		return nationality;
	}
	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}
	public String getCurrentResidence() {
		return currentResidence;
	}
	public void setCurrentResidence(String currentResidence) {
		this.currentResidence = currentResidence;
	}
}
