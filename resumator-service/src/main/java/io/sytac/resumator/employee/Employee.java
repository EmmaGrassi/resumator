package io.sytac.resumator.employee;

import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.Experience;
import io.sytac.resumator.model.Language;
import io.sytac.resumator.model.enums.Nationality;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * One employee. Acts as the Aggregate Root for the Employee aggregate
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class Employee {

	private final String id;
	private final String title;
	private final String name;
	private final String surname;
	private final String email;
	private final String phoneNumber;
	private final String gitHub;
	private final String linkedIn;
	private final Date dateOfBirth;
	private final Nationality nationality;
	private final String currentResidence;
	private final String aboutMe;
	private final List<Education> educations;
	private final List<Course> courses;
	private final List<Experience> experiences;
	private final List<Language> languages;

	/**
	 * Create a new employee, assigning a random id
	 *
	 * @param name The name of the employee
	 * @param surname The last name of the employee
	 * @param email The date of birth of the employee
	 * @param phoneNumber The phone number of the employee
	 * @param gitHub The GitHub account of the employee
	 * @param linkedIn The LinkedIn account of the employee
	 * @param dateOfBirth The date of birth of the employee
	 * @param nationality The nationality of the employee
	 * @param aboutMe The short introduction of the employee
	 * @param educations The list of educations of the employee
	 * @param courses The list of courses of the employee
	 * @param experiences The experiences list of the employee
	 * @param languages The list of languages of the employee
     */
	public Employee(final String title,
					final String name,
					final String surname,
					final String email,
					final String phoneNumber,
					final String gitHub,
					final String linkedIn,
					final Date dateOfBirth,
					final Nationality nationality,
					final String currentResidence,
					final String aboutMe,
					final List<Education> educations,
					final List<Course> courses,
					final List<Experience> experiences,
					final List<Language> languages) {

		this(UUID.randomUUID().toString(), title, name, surname, email, phoneNumber, gitHub, linkedIn,
				dateOfBirth, nationality, currentResidence, aboutMe, educations, courses, experiences, languages);
	}

	/**
	 *
	 * @param id               The technical ID of the employee
	 * @param name             The name of the employee
	 * @param surname          The last name of the employee
	 * @param dateOfBirth      The date of birth of the employee
	 * @param nationality      The nationality of the employee
	 * @param currentResidence The city of current residence of the employee
	 */

	/**
	 * Create an employee with a fixed technical ID
	 *
	 * @param id The technical ID of the employee
	 * @param name The name of the employee
	 * @param surname The last name of the employee
	 * @param email The date of birth of the employee
	 * @param phoneNumber The phone number of the employee
	 * @param gitHub The GitHub account of the employee
	 * @param linkedIn The LinkedIn account of the employee
	 * @param dateOfBirth The date of birth of the employee
	 * @param nationality The nationality of the employee
	 * @param aboutMe The short introduction of the employee
	 * @param educations The list of educations of the employee
     * @param courses The list of courses of the employee
     * @param experiences The experiences list of the employee
     * @param languages The list of languages of the employee
     */
	public Employee(final String id,
					final String title,
					final String name,
					final String surname,
					final String email,
					final String phoneNumber,
					final String gitHub,
					final String linkedIn,
					final Date dateOfBirth,
					final Nationality nationality,
					final String currentResidence,
					final String aboutMe,
					final List<Education> educations,
					final List<Course> courses,
					final List<Experience> experiences,
					final List<Language> languages) {
		this.id = id;
		this.title = title;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gitHub = gitHub;
		this.linkedIn = linkedIn;
		this.dateOfBirth = dateOfBirth;
		this.nationality = nationality;
		this.currentResidence = currentResidence;
		this.aboutMe = aboutMe;
		this.educations = educations;
		this.courses = courses;
		this.experiences = experiences;
		this.languages = languages;
	}

    public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getGitHub() {
		return gitHub;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public Nationality getNationality() {
		return nationality;
	}

	public String getCurrentResidence() {
		return currentResidence;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public List<Education> getEducations() {
		return educations;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public List<Experience> getExperiences() {
		return experiences;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	// TODO: add all the fields
	@Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", nationality=" + nationality +
                '}';
    }
}
