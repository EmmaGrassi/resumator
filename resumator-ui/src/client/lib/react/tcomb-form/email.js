const t = require('tcomb-form');

module.exports = email = t.refinement(t.String, validator.isEmail);

email.getValidationErrorMessage = (value, path, context) => 'Invalid Email';
