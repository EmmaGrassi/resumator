import cookies from 'cookies-js';
import { pushPath } from 'redux-simple-router';

import handleRequestError from '../../lib/handleRequestError.js';
import cookieClear from '../../services/user/cookie/clear';
import cookieSet from '../../services/user/cookie/set';
import cookieGet from '../../services/user/cookie/get';
import serverLogin from '../../services/user/login/serverLogin';
import profileGet from '../../services/user/profile/get';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

function login(data) {
  return (dispatch) => {
     dispatch({ type: 'user:login:start' })

    serverLogin(data, function(error, googleProfile) {
      if (error) {
       
    	  dispatch({ type: 'user:login:failure' });

          return;
        }
      //cookies are sent in server side
      const cookieData = cookieGet();
      
      profileGet(googleProfile.email, function(error, profile) {
          if (error) {
            if (error.error.status === 404) {
              dispatch(pushPath(`/employees/new`));
              dispatch({ type: 'user:getProfile:start', payload: profile });
              dispatch({ type: 'user:login:success', payload: cookieData });

              return;
            } else {
              cookieClear();

              dispatch({ type: 'user:login:failure' });

              return;
            }
          }

          dispatch({ type: 'user:getProfile:success', payload: profile });
          dispatch({ type: 'user:login:success', payload: cookieData });

          if (profile) {
            if (profile.admin) {
              dispatch(pushPath(`/employees`));
            } else {
              dispatch(pushPath(`/employees/${profile.email}`));
            }
          }
        });
    });
     
  };
}

export default login;
