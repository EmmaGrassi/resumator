import _ from 'lodash';

const message = { message: 'Should contain unique elements.' };

function fn(propertyName) {
  return (fail) => {
    const inputList = this[propertyName];
    const uniqueList = _.uniq(inputList);

    if (inputList.length !== uniqueList.length) {
      fail();
    }
  };
}

function containsUnique(instance, property) {
  instance.validate(property, fn(property), message);
}

export default containsUnique;
