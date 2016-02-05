import cookies from 'cookies-js';
import { pushPath } from 'redux-simple-router';

import handleRequestError from '../../lib/handleRequestError.js';
import cookieClear from '../../services/user/cookie/clear';
import cookieSet from '../../services/user/cookie/set';
import profileGet from '../../services/user/profile/get';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

function login(data) {
  return async (dispatch) => {
    try {
      dispatch({ type: 'user:login:start' })

      await cookieSet(data);
      await profileGet(data.email);

      dispatch({ type: 'user:login:success' });

      dispatch(pushPath(`/`));
    } catch(error) {
      await cookieClear();

      dispatch({ type: 'user:login:failure' });
    }
  };
}

export default login;
