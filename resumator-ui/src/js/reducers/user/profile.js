import immutable from 'immutable';

import employee from '../common/employee';

const defaults = immutable.Map({
  isFetching: false,
  item: employee,
});

function profile(state = defaults, action = {}) {
  switch (action.type) {
    case 'user:getProfile:start':
      return state
        .set('isFetching', true);

    case 'user:getProfile:success':
      return state
        .set('isFetching', false)
        .set('item', immutable.fromJS(action.payload));

    case 'user:getProfile:failure':
      return defaults;

    default:
      return state;
  }
}

export default profile;
