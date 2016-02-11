import immutable from 'immutable';
import { map } from 'lodash';

const defaults = immutable.Map({
  isFetching: false,

  items: []
});

function list(state = defaults, action = {}) {
  switch (action.type) {
    case 'employees:list:start':
      return state
        .set('isFetching', true);

    case 'employees:list:success':
      return state
        .set('isFetching', false)
        .set('items', immutable.fromJS(action.payload));

    case 'employees:list:failure':
      return state
        .set('isFetching', false);

    default:
      return state;
  }
}

export default list;
