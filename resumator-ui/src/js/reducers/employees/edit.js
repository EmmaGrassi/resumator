import immutable from 'immutable';

import employee from '../common/employee';

const defaults = immutable.Map({
  isFetching: false,
  isSaving: false,
  hasFailed: false,
  email: null,
  item: employee,
  errors: immutable.Map(),
});

function edit(state = defaults, action = {}) {
  switch (action.type) {
    case 'employees:edit:start':
      return state
        .set('isFetching', true);

    case 'employees:edit:success':
      return state
        .set('isFetching', false)
        .set('item', immutable.fromJS(action.payload));

    case 'employees:edit:failure':
      return state
        .set('isFetching', false)
        .set('hasFailed', true)
        .set('errors', action.errors);

    case 'employees:edit:change':
      return state
        .setIn(['item', action.payload.key], action.payload.value);

    case 'employees:update:start':
      return state
        .set('isSaving', true);

    case 'employees:update:success':
      return state
        .set('isSaving', false)
        .set('hasFailed', false);

    case 'employees:update:failure':
      return state
        .set('isSaving', false)
        .set('hasFailed', true)
        .set('errors', action.errors);

    default:
      return state;
  }
}

export default edit;
