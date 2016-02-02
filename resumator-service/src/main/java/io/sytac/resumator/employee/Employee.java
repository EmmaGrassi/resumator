package io.sytac.resumator.employee;

import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.Experience;
import io.sytac.resumator.model.Language;
import io.sytac.resumator.model.enums.Nationality;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
@Getter
@EqualsAndHashCode
@ToString
public class Employee {

	private final String id;
	private final EmployeeType type;
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
	private final boolean admin;

	@Builder
	Employee(String id, EmployeeType type, String title, String name, String surname, String email, String phoneNumber,
			 String gitHub, String linkedIn, Date dateOfBirth, Nationality nationality, String currentResidence,
			 String aboutMe, List<Education> educations, List<Course> courses,
			 List<Experience> experiences, List<Language> languages, boolean admin) {
		this.id = id == null ? UUID.randomUUID().toString() : id;
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
		this.admin = admin;

		if (type != null) {
			this.type = type;
		} else {
			if (email.endsWith("sytac.io")) {
				this.type = EmployeeType.EMPLOYEE;
			} else {
				this.type = EmployeeType.PROSPECT;
			}
		}
	}
}
