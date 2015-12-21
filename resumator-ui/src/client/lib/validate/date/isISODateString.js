const pattern = `^([0-9]{4})-([0-9]{2})-([0-9]{2})T([0-9]{2})\:([0-9]{2})\:([0-9]{2})\\+([0-9]{2})\:([0-9]{2})`;
const regExp = new RegExp(pattern);
const message = { message: 'Should be an ISO date (yyyy-mm-ddThh:mm:ss+hh:mm).' };

function fn(propertyName) {
  return (fail) => {
    if (regExp.exec(this[propertyName])) {
      fail();
    }
  };
}

function iso(instance, property) {
  instance.validate(property, fn(property), message);
}

module.exports = iso;
