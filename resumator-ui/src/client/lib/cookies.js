const cookies = require('cookies-js');

function getToken() {
  return cookies('token');
}

function setToken(token, ttl) {
  cookies('token', token, {
    expires: ttl
  });
}

function clearToken() {
  cookies('token', '', {
    expires: new Date(0)
  });
}

module.exports = {
  getToken,
  setToken,
  clearToken
};
