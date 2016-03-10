package io.sytac.resumator.employee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.utils.DateUtils;

/**
 * Validates an {@link Employee}
 *
 * @author Selman Tayyar
 */
public class EmployeeValidator {

    public static Map<String, String> validateEmployee(EmployeeCommandPayload employee) {
	Map<String, String> fields = new HashMap<>();

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	Set<ConstraintViolation<EmployeeCommandPayload>> violations = validator.validate(employee);

	for (ConstraintViolation<EmployeeCommandPayload> constraintViolation : violations) {
	    fields.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
	}

	if (!fields.containsKey("dateOfBirth")) {//if there is already a validation error on that field,we skip.

	    if (!validateDateFormat(employee.getDateOfBirth())) {
		fields.put("dateOfBirth", "dateOfBirth should be in correct format ");
	    } else if (validateDateNotInFuture(employee.getDateOfBirth())) {
		fields.put("dateOfBirth", "dateOfBirth can not be in the future");
	    }
	}

	if (!fields.containsKey("nationality")) {
	    String nationality = employee.getNationality();
	    try {
		Nationality.valueOf(nationality);
	    } catch (IllegalArgumentException e) {

		fields.put("nationality", "Nationality should be within the accepted list.");
	    }
	}
	List<Education> educations = Optional.ofNullable(employee.getEducation()).orElse(new ArrayList<Education>());

	for (Education education : educations) {
	    String fieldErrorIndex = "education[" + educations.indexOf(education) + "].startYear";
	    if (!fields.containsKey(fieldErrorIndex)) {
		if (education.getStartYear() > education.getEndYear()) {

		    fields.put(fieldErrorIndex, "startyear can not be later than endyear");
		}
	    }

	}

	List<Course> courses = Optional.ofNullable(employee.getCourses()).orElse(new ArrayList<Course>());

	for (Course course : courses) {
	    String fieldErrorIndex = "course[" + courses.indexOf(course) + "].year";
	    if (!fields.containsKey(fieldErrorIndex)) {
		if (new GregorianCalendar().get(Calendar.YEAR) < course.getYear()) {
		    fields.put(fieldErrorIndex, "course year can not be in the future");

		}
	    }

	}

	return fields;

    }

    public static boolean validateDateFormat(String dateStr) {

	try {
	    DateUtils.convert(dateStr);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    return false;
	}

	return true;

    }

    public static boolean validateDateNotInFuture(String dateStr) {
	Date date = DateUtils.convert(dateStr);
	Date now = new Date();
	return date.after(now);

    }

}
