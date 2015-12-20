import t from 'tcomb-form';

export default email = t.refinement(t.String, validator.isEmail);

email.getValidationErrorMessage = (value, path, context) => 'Invalid Email';
