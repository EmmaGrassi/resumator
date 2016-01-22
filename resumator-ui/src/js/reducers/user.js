import immutable from 'immutable';

const defaults = immutable.Map({
  token: null,

  id: null,
  name: null,
  surname: null,
  imageUrl: null,
  email: null
});

function user(state = defaults, action = {}) {
  switch (action.type) {
    case 'user:login:success':
      return state
        .set('token', action.data.token)
        .set('id', action.data.id)
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
