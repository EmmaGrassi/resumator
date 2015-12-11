import cookies from 'cookies-js';

export function getToken() {
  return cookies('token');
}

export function setToken(token, ttl) {
  cookies('token', token, {
    expires: ttl
  });
}

export function clearToken() {
  cookies('token', '', {
    expires: new Date(0)
  });
}
