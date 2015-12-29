const log = require('loglevel');
const store = require('../lib/store');

// const { promiseFromNodeCallback } = require('../../../common/lib/promise');
const { request } = require('../lib/redux/actions');

function newEmployee(data) {
  return { type: 'employee:new', data };
};

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

module.exports = Object.assign(
  {
    newEmployee,
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
