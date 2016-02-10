package io.sytac.resumator.employee;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.api.client.util.Maps;
import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.user.ProfileCommandPayload;
import io.sytac.resumator.user.ProfileValidator;
import io.sytac.resumator.utils.DateUtils;
import io.sytac.resumator.validator.FieldsValidator;

/**
 * Validates an {@link Employee}
 *
 * @author Selman Tayyar
 * @author Dmitry Ryazanov
 */
public class EmployeeValidator {

    public static Map<String, String> validateEmployee(EmployeeCommandPayload employee) {
        final FieldsValidator<EmployeeCommandPayload> fieldsValidator = new FieldsValidator<>();
        fieldsValidator.validate(employee);

        employee.getEducation().forEach(education -> {
            if (education.getStartYear() > education.getEndYear()) {
                fieldsValidator.addFailure("education.startyear", "start year can not be later than end year");
            }
        });

        employee.getCourses().forEach(course -> {
            if (new GregorianCalendar().get(Calendar.YEAR)<course.getYear()) {
                fieldsValidator.addFailure("course.year", "course year can not be in the future");
            }
        });

        return fieldsValidator.getFailures();
    }
}
