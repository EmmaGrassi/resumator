import { pushPath } from 'redux-simple-router';

import cookieClear from '../../services/user/cookie/clear';

import cookieGet from '../../services/user/cookie/get';
import serverLogin from '../../services/user/login/serverLogin';
import profileGet from '../../services/user/profile/get';
import showAlert from '../alerts/show';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname,
};

function showLoginFailed(dispatch) {
  const message = {
    level: 'warning',
    message: 'Login failed',
    id: 'login:failed',
  };
  return dispatch(showAlert(message));
}

function showLoginSuccess(dispatch, email) {
  const message = {
    level: 'success',
    message: `Logged in with email: ${email}`,
    id: 'login:success',
  };
  return dispatch(showAlert(message));
}


export default function login(data) {
  return (dispatch) => {
    dispatch({ type: 'user:login:start' });

    serverLogin(data, (error, googleProfile) => {
      if (error) {
        dispatch({ type: 'user:login:failure', error });
        showLoginFailed(dispatch);
        return;
      }

      const cookieData = cookieGet();

      profileGet(googleProfile.email, (error, profile) => {
        if (error) {
          if (error.error.status === 404) {
            dispatch(pushPath('/employees/register'));

            dispatch({ type: 'user:getProfile:success', payload: {} });
            dispatch({ type: 'user:login:success', payload: cookieData });

            showLoginSuccess(dispatch, googleProfile.email);

            return;
          }

          cookieClear();

          dispatch({ type: 'user:login:failure' });
          showLoginFailed(dispatch);

          return;
        }

        dispatch({ type: 'user:getProfile:success', payload: profile });
        dispatch({ type: 'user:login:success', payload: cookieData });

        showLoginSuccess(dispatch, googleProfile.email);

        if (profile) {
          if (profile.admin) {
            dispatch(pushPath('/employees'));
          } else {
            dispatch(pushPath(`/employees/${profile.email}`));
          }
        }
      });
    });
  };
}
