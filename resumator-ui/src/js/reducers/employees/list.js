import immutable from 'immutable';
import { map } from 'lodash';

const defaults = immutable.Map({
  isFetching: false,

  items: immutable.List()
});

function list(state = defaults, action = {}) {
  switch (action.type) {
    case 'employees:list:start':
      return state
        .set('isFetching', true);

    case 'employees:list:success':
      const json = JSON.parse(action.response.text);

      let employees;
      if (json._embedded && json._embedded.employees) {
        employees = map(json._embedded.employees, (v) => {
          delete v._links;

          return v;
        });
      } else {
        employees = [];
      }

      return state
        .set('isFetching', false)
        .set('items', immutable.fromJS(employees));

    case 'employees:list:failure':
      return state
        .set('isFetching', false);

    default:
      return state;
  }
}

export default list;
