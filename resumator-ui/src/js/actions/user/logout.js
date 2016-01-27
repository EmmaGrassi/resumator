import cookies from 'cookies-js';
import { pushPath } from 'redux-simple-router';

import clearCookie from './clearCookie';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

// TODO: Sign out with google first.
function logout(data) {
  return (dispatch) => {
    dispatch(clearCookie());

    dispatch({ type: 'user:logout:success', data });

    dispatch(pushPath(`/`));
  };
}

export default logout;
