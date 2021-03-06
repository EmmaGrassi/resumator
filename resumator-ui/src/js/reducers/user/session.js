import immutable from 'immutable';

const defaults = immutable.Map({
  idToken: null,
  name: null,
  surname: null,
  email: null,
  xsrf: null,
});

function session(state = defaults, { type, payload } = {}) {
  switch (type) {
    case 'user:initialize:success':
      return state
        .set('idToken', payload.idToken)
        .set('name', payload.name)
        .set('surname', payload.surname)
        .set('email', payload.email);

    case 'user:login:success':
      return state
        .set('idToken', payload.idToken)
        .set('name', payload.name)
        .set('surname', payload.surname)
        .set('email', payload.email);

    case 'user:login:failure':
      return defaults;

    case 'user:setCookie:error':
      return defaults;

    case 'user:logout:success':
      return defaults;

    case 'user:clearCookie:success':
      return defaults;
    case 'user:xsrf-token:received':
      return state.set('xsrf', payload);

    default:
      return state;
  }
}

export default session;

// maak 1 actions voor de login procedure, niet alle actions hoeven de state te
// updaten.
