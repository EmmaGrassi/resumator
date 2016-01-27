import cookies from 'cookies-js';

import login from './login';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

function clearCookie() {
  return (dispatch) => {
    cookies.expire('idToken', cookiesOptions);
    cookies.expire('expiresAt', cookiesOptions);
    cookies.expire('email', cookiesOptions);
    cookies.expire('name', cookiesOptions);
    cookies.expire('surname', cookiesOptions);
    cookies.expire('imageUrl', cookiesOptions);
    cookies.expire('resumatorJWT', cookiesOptions);

    dispatch({ type: 'user:cookie:cleared' });
  };
}

export default clearCookie;
