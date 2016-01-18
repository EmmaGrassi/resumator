package io.sytac.resumator.employee;

import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.Experience;
import io.sytac.resumator.model.Language;
import io.sytac.resumator.model.enums.Nationality;

import java.util.List;

/**
 * One employee. Acts as the Aggregate Root for the Employee aggregate
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class Employee {

	private final EmployeeId id;
	private final String title;
	private final String name;
	private final String surname;
	private final String email;
	private final String phonenumber;
	private final String github;
	private final String linkedin;
	private final Long dateOfBirth;
	private final Nationality nationality;
	private final String aboutMe;
	private final List<Education> education;
	private final List<Course> courses;
	private final List<Experience> experience;
	private final List<Language> languages;

	/**
	 * Create a new employee, assigning a random id
	 *
	 * @param name The name of the employee
	 * @param surname The last name of the employee
	 * @param email The date of birth of the employee
	 * @param phonenumber The phone number of the employee
	 * @param github The GitHub account of the employee
	 * @param linkedin The LinkedIn account of the employee
	 * @param dateOfBirth The date of birth of the employee
	 * @param nationality The nationality of the employee
	 * @param aboutMe The short introduction of the employee
	 * @param education The list of education of the employee
	 * @param courses The list of courses of the employee
	 * @param experience The experience list of the employee
	 * @param languages The list of languages of the employee
     */
	public Employee(final String title,
					final String name,
					final String surname,
					final String email,
					final String phonenumber,
					final String github,
					final String linkedin,
					final Long dateOfBirth,
					final Nationality nationality,
					final String aboutMe,
					final List<Education> education,
					final List<Course> courses,
					final List<Experience> experience,
					final List<Language> languages) {

		this(new EmployeeId(), title, name, surname, email, phonenumber, github, linkedin,
				dateOfBirth, nationality, aboutMe, education, courses, experience, languages);
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
	 * @param phonenumber The phone number of the employee
	 * @param github The GitHub account of the employee
	 * @param linkedin The LinkedIn account of the employee
	 * @param dateOfBirth The date of birth of the employee
	 * @param nationality The nationality of the employee
	 * @param aboutMe The short introduction of the employee
	 * @param education The list of education of the employee
     * @param courses The list of courses of the employee
     * @param experience The experience list of the employee
     * @param languages The list of languages of the employee
     */
	public Employee(final EmployeeId id,
					final String title,
					final String name,
					final String surname,
					final String email,
					final String phonenumber,
					final String github,
					final String linkedin,
					final Long dateOfBirth,
					final Nationality nationality,
					final String aboutMe,
					final List<Education> education,
					final List<Course> courses,
					final List<Experience> experience,
					final List<Language> languages) {
		this.id = id;
		this.title = title;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phonenumber = phonenumber;
		this.github = github;
		this.linkedin = linkedin;
		this.dateOfBirth = dateOfBirth;
		this.nationality = nationality;
		this.aboutMe = aboutMe;
		this.education = education;
		this.courses = courses;
		this.experience = experience;
		this.languages = languages;
	}

	public EmployeeId getId() {
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

	public String getPhonenumber() {
		return phonenumber;
	}

	public String getGithub() {
		return github;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public Long getDateOfBirth() {
		return dateOfBirth;
	}

	public Nationality getNationality() {
		return nationality;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public List<Education> getEducation() {
		return education;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public List<Experience> getExperience() {
		return experience;
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
