/*
 * Actions should emit events, so they should be methods of a class that is an
 * EventEmitter.
 * The Store is an EventEmitter with a data property. There's supposed to be
 * only one in the app and it houses all the data/state.
 * The store needs a way to translate action events into data changes and it
 * needs a way to communicate these changes in the data. Since the data is a
 * tree, one way to do this could be to emit an event in the form of
 * `'level.sublevel.prop[0].etc', ...args`. This way a querying/subscription
 * DSL can be made with wildcards to receive updates over ranges of the tree.
 *
 * Since the source of the
 * action events
 */

import log from 'loglevel';
import * as store from './lib/store';

// import { promiseFromNodeCallback } from '../lib/promise';

import { request } from './lib/redux/actions';

function clearAuthentication() {
  localStorage.setItem('authentication', null);

  return { type: 'authentication:clear' };
}

function getAuthentication() {
  let authentication = localStorage.getItem('authentication');

  if (authentication) {
    authentication = JSON.parse(authentication);
  }

  return { type: 'authentication:get', authentication };
}

function setAuthentication(authentication) {
  localStorage.setItem('authentication', JSON.stringify(authentication));

  return { type: 'authentication:set' };
}

function getHost() {
  const { protocol, hostname, port } = window.location;

  return `${protocol}//${hostname}:${port}`;
}

const loginRequest = request({
  name: 'login',

  getOptions: (email, password) => {
    return {
      uri: `${getHost()}/api/CMSUsers/login`,
      method: 'POST',
      json: true,
      body: {
        email: email,
        password: password
      }
    };
  }
});

// TODO: Got login? Ditch stupid repetitive patterns. Just make it work now.
const login = {
  login: function login(...args) {
    return dispatch => {
      return dispatch(loginRequest.login(...args))
        .then(response => {
          const { id, ttl, created, userId } = response.body;

          dispatch(setAuthentication({ token: id, ttl, created, userId }));
        });
    };
  }
};

// TODO: Abstract all requests so that when logged in the Authorization header is set.
const logoutRequest = request({
  name: 'logout',

  getOptions: (token) => {
    return {
      uri: `${getHost()}/api/CMSUsers/logout`,
      method: 'POST',
      json: true,
      headers: {
        'Authorization': token
      }
    };
  }
});

const logout = {
  logout: function logout(...args) {
    return dispatch => {
      return dispatch(logoutRequest.logout(...args))
        .then(response => dispatch(clearAuthentication()));
    };
  }
};

const cmsList = request({
  name: 'cmsList',

  getOptions: (authentication, resource, page, limit) => {
    return {
      uri: `${getHost()}/api/${resource}`,
      method: 'GET',
      json: true,
      headers: {
        'Authorization': authentication.token
      }
    };
  }
});

const cmsShow = request({
  name: 'cmsShow',

  getOptions: (id) => {
    return {
      uri: `${getHost()}/api/CMSUsers/${id}}`,
      method: 'GET',
      json: true
    };
  }
});

const cmsUpdate = request({
  name: 'cmsUpdate',

  getOptions: (data) => {
    return {
      uri: `${getHost()}/api/CMSUsers/${data.id}}`,
      method: 'PUT',
      json: true,
      data: data
    };
  }
});

export default Object.assign(
  {
    clearAuthentication,
    getAuthentication,
    setAuthentication
  },
  login,
  logout,
  cmsList,
  cmsShow,
  cmsUpdate
);
