package io.sytac.resumator.employee;

import io.sytac.resumator.model.enums.Nationality;

/**
 * One employee. Acts as the Aggregate Root for the Employee aggregate
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Employee {

	private final EmployeeId id;
	private final String name;
	private final String surname;
	private final Integer yearOfBirth;
	private final Nationality nationality;
	private final String currentResidence;

	/**
	 * Create a new employee, assigning a random id
     * @param name             The name of the employee
     * @param surname          The last name of the employee
     * @param yearOfBirth      The year of birth of the employee
     * @param nationality      The nationality of the employee
     * @param currentResidence The city of current residence of the employee
     */
	public Employee(final String name,
                    final String surname,
                    final Integer yearOfBirth,
                    final Nationality nationality,
                    final String currentResidence) {
		this(new EmployeeId(), name, surname, yearOfBirth, nationality, currentResidence);
	}

    /**
     * Create an employee with a fixed technical ID
     * @param id               The technical ID of the employee
     * @param name             The name of the employee
     * @param surname          The last name of the employee
     * @param yearOfBirth      The year of birth of the employee
     * @param nationality      The nationality of the employee
     * @param currentResidence The city of current residence of the employee
     */
	public Employee(final EmployeeId id,
                    final String name,
                    final String surname,
                    final Integer yearOfBirth,
                    final Nationality nationality,
                    final String currentResidence) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.yearOfBirth = yearOfBirth;
		this.nationality = nationality;
		this.currentResidence = currentResidence;
    }

	public EmployeeId getId() {
		return id;
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", nationality=" + nationality +
                ", currentResidence='" + currentResidence + '\'' +
                '}';
    }
}
