// TODO: redo with Immutable.js

const defaults = {
  token: null,

  id: null,
  name: null,
  surname: null,
  imageUrl: null,
  email: null
};

function user(state = defaults, action = {}) {
  switch (action.type) {
    case 'user:login:success':
      // TODO: Use immutable.js
      return Object.assign({}, state, {
        token: action.data.token,

        id: action.data.id,
        name: action.data.name,
        surname: action.data.surname,
        imageUrl: action.data.imageUrl,
        email: action.data.email
      });

    case 'user:login:error':
      return defaults;

    case 'user:logout:success':
      return defaults;

    default:
      return state;
  }
}

export default user;
