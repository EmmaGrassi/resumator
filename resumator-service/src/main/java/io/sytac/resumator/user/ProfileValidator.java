package io.sytac.resumator.user;

import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.utils.DateUtils;
import io.sytac.resumator.validator.FieldsValidator;

import java.util.Date;
import java.util.Map;

/**
 * Validates an {@link Profile}
 *
 * @author Dmitry Ryazanov
 */
public class ProfileValidator {

	public static Map<String, String> validateProfile(ProfileCommandPayload profile) {
        final FieldsValidator<ProfileCommandPayload> fieldsValidator = new FieldsValidator<>();
        fieldsValidator.validate(profile);
	    
        if (validateDateNotInFuture(profile.getDateOfBirth())) {
            fieldsValidator.addFailure("dateOfBirth", "date of birth can not be in the future");
        }

        try {
            Nationality.valueOf(profile.getNationality());
        } catch (IllegalArgumentException e) {
            fieldsValidator.addFailure("nationality", "nationality should be within the accepted list.");
        }

		return fieldsValidator.getFailures();
	}

	public static boolean validateDateNotInFuture(String dateStr) {
		return DateUtils.convert(dateStr).after(new Date());
	}
}
