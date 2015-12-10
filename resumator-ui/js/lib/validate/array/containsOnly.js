import _ from 'lodash';

function fn(propertyName) {
  return (fail) => {
    const input = this[propertyName];
    const difference = _.difference(input, weekDays);

    if (difference.length) {
      fail();
    }
  };
}

function containsOnly(instance, property, elements) {
  instance.validate(property, fn(property, elements), { message: `Should only contain the elements "${elements.join('", "')}".` });
}

export default containsOnly;
