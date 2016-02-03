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
	
	public static Map<String, String> validateEmployee(EmployeeCommandPayload employee){
		Map<String,String> fields= new HashMap<>();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator validator = factory.getValidator();
	    Set<ConstraintViolation<EmployeeCommandPayload>> violations = validator.validate(employee);
	    
	    for (ConstraintViolation<EmployeeCommandPayload> constraintViolation : violations) {
	    	 fields.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
		}
	    
	    if(fields.isEmpty()){
	    	if(validateDateNotInFuture(employee.getDateOfBirth()))
	    		fields.put("dateOfBirth", "dateOfBirth can not be in the future");
	    	
	    	String nationality=employee.getNationality();
	    	try {
				Nationality.valueOf(nationality);
			} catch (IllegalArgumentException e) {
				
				fields.put("nationality", "Nationality should be within the accepted list.");
			}
	    	
	    	List<Education> educations=employee.getEducation();
	    	
	    	educations.forEach(education -> {
	            if(education.getStartYear()>education.getEndYear()) {
	            	fields.put("Education.startyear", "startyear can not be later than endyear");
	            }
	    	});
	    	
	    	List<Course> courses=employee.getCourses();
	    	
	    	courses.forEach(course -> {
	    		if(new GregorianCalendar().get(Calendar.YEAR)<course.getYear()){
	    			fields.put("course.year", "course year can not be in the future");
	            }
	    	});
	    }  	

		
		return fields;
		
	}

	public static boolean validateDateNotInFuture(String dateStr) {
		Date date = DateUtils.convert(dateStr);
		Date now = new Date();
		return date.after(now);

	}

}
