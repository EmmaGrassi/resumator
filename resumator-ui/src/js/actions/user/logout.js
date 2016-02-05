import cookies from 'cookies-js';
import { pushPath } from 'redux-simple-router';

import cookieClear from '../../services/user/cookie/clear';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

// TODO: Sign out with google first.
function logout(data) {
  return async (dispatch) => {
    dispatch({ type: 'user:logout:start' })

    await cookieClear();

    dispatch({ type: 'user:logout:success', data });

    dispatch(pushPath(`/`));
  };
}

export default logout;
