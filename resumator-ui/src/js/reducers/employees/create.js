import immutable from 'immutable';

import employee from '../common/employee';

const defaults = immutable.Map({
  isSaving: false,
  hasFailed: false,
  email: null,
  item: employee,
  errors: immutable.Map(),
});

function create(state = defaults, action = {}) {
  switch (action.type) {
    case 'employees:create:start':
      return state
        .set('isSaving', true);

    case 'employees:create:success':
      return state
        .set('isSaving', false)
        .set('hasFailed', false)
        .set('email', action.payload)
        .set('errors', immutable.Map());

    case 'employees:create:failure':
      return state
        .set('isSaving', false)
        .set('hasFailed', true)
        .set('errors', action.errors);

    default:
      return state;
  }
}

export default create;
