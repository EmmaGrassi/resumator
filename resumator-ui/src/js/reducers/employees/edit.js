import immutable from 'seamless-immutable';

import employee from '../common/employee';

const defaults = immutable({
  isFetching: false,

  item: employee
});

function edit(state = defaults, action = {}) {
  switch (action.type) {
    case 'employees:edit:start':
      return state
        .set('isFetching', true);

    case 'employees:edit:success':
      return state
        .set('isFetching', false)
        .set('item', immutable(action.response));

    case 'employees:edit:failure':
      return state
        .set('isFetching', false);

    default:
      return state;
  }
}

export default edit;
