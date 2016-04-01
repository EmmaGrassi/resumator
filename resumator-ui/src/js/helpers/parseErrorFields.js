import { isEmpty } from 'lodash';

export default function parseFields(fields, state) {
  const destinationListsKeys = ['education', 'experience', 'courses', 'languages'];
  const finalErrors = {};
  Object.keys(fields)
    .filter(k => !k.includes('.'))
    .forEach(k => {
      finalErrors[k] = fields[k];
    });
  destinationListsKeys.forEach(typeKey => {
    finalErrors[typeKey] = [];
    const count = state.getIn('item', typeKey).count || 1;
    for (let i = 0; i < count; i++) {
      finalErrors[typeKey].push(Object
      .keys(fields)
      .filter(key => key.includes(`${typeKey}[${i}]`))
      .reduce((prev, current) => {
        prev[current.substr(current.indexOf('.')).replace('.', '')] = fields[current];
        return prev;
      }, {}));
    }
    if (isEmpty(finalErrors[typeKey][0])) delete finalErrors[typeKey];
  });
  return finalErrors;
}
