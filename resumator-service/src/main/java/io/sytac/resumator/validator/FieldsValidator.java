package io.sytac.resumator.validator;

import com.google.api.client.util.Maps;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;
import java.util.Set;

/**
 * Common class to run validation for the all annotated fields of the class T
 *
 * @author Dmitry Ryazanov
 */
public class FieldsValidator<T> {

    private Map<String, String> failures = Maps.newHashMap();

    public void validate(T instanceToValidate) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<T>> violations = validator.validate(instanceToValidate);

        for (ConstraintViolation<T> constraintViolation : violations) {
            final String property = constraintViolation.getPropertyPath().toString();
            addFailure(property, constraintViolation.getMessage());
        }
    }

    public void addFailure(String property, String message) {
        failures.put(property, message);
    }

    public Map<String, String> getFailures() {
        return this.failures;
    }
}