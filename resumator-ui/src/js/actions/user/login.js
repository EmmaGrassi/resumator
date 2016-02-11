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
  return (dispatch) => {
    dispatch({ type: 'user:login:start' })

    cookieSet(data);

    profileGet(data.email, function(error, profile) {
      if (error) {
        if (error.error.status === 404) {
          dispatch(pushPath(`/employees/new`));
          dispatch({ type: 'user:getProfile:success', payload: profile });
          dispatch({ type: 'user:login:success', payload: data });

          return;
        } else {
          cookieClear();

          dispatch({ type: 'user:login:failure' });

          return;
        }
      }

      dispatch({ type: 'user:getProfile:success', payload: profile });
      dispatch({ type: 'user:login:success', payload: data });

      if (profile) {
        if (profile.admin) {
          dispatch(pushPath(`/employees`));
        } else {
          dispatch(pushPath(`/employees/${profile.email}`));
        }
      }
    });
  };
}

export default login;
