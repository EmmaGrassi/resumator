import cookies from 'cookies-js';
import { pushPath } from 'redux-simple-router';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

function logout(data) {
  return (dispatch) => {
    cookies.expire('token', cookiesOptions);
    cookies.expire('id', cookiesOptions);
    cookies.expire('email', cookiesOptions);
    cookies.expire('name', cookiesOptions);
    cookies.expire('surname', cookiesOptions);
    cookies.expire('imageUrl', cookiesOptions);

    cookies.expire('resumatorJWT', cookiesOptions);

    dispatch({ type: 'user:logout:success', data });

    dispatch(pushPath(`/`));
  };
}

export default logout;
