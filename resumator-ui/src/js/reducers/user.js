import immutable from 'immutable';

const defaults = immutable.Map({
  idToken: null,
  expiresAt: null,

  name: null,
  surname: null,
  imageUrl: null,
  email: null
});

function user(state = defaults, action = {}) {
  switch (action.type) {
    case 'user:login:success':
      return state
        .set('idToken', action.data.idToken)
        .set('expiresAt', action.data.expiresAt)
        .set('name', action.data.name)
        .set('surname', action.data.surname)
        .set('imageUrl', action.data.imageUrl)
        .set('email', action.data.email);

    case 'user:login:error':
      return defaults;

    case 'user:logout:success':
      return defaults;

    default:
      return state;
  }
}

export default user;
