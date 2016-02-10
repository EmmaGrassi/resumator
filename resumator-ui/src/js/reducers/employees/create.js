import immutable from 'seamless-immutable';

import employee from '../common/employee';

const defaults = immutable({
  isSaving: false,

  // TODO: Check if this is needed.
  email: null,

  item: employee
});

function create(state = defaults, action = {}) {
  switch (action.type) {
    case 'employees:create:start':
      return state
        .set('isSaving', true);

    case 'employees:create:success':
      const email = action.response.email;

      return state
        .set('isSaving', false)
        .set('email', email);

    case 'employees:create:failure':
      return state
        .set('isSaving', false);

    default:
      return state;
  }
}

export default create;
