import immutable from 'seamless-immutable';

const defaults = immutable({
  idToken: null,
  expiresAt: null,

  name: null,
  surname: null,
  imageUrl: null,
  email: null
});

function session(state = defaults, { type, payload } = {}) {
  switch (type) {
    case 'user:initialize:success':
      return state
        .set('idToken', payload.idToken)
        .set('expiresAt', payload.expiresAt)
        .set('name', payload.name)
        .set('surname', payload.surname)
        .set('imageUrl', payload.imageUrl)
        .set('email', payload.email);

    case 'user:login:success':
      return state
        .set('idToken', payload.idToken)
        .set('expiresAt', payload.expiresAt)
        .set('name', payload.name)
        .set('surname', payload.surname)
        .set('imageUrl', payload.imageUrl)
        .set('email', payload.email);

    case 'user:login:failure':
      return defaults;

    case 'user:setCookie:error':
      return defaults;

    case 'user:logout:success':
      return defaults;

    case 'user:clearCookie:success':
      return defaults;

    default:
      return state;
  }
}

export default session;

// maak 1 actions voor de login procedure, niet alle actions hoeven de state te
// updaten.
