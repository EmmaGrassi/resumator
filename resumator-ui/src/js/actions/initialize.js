import cookies from 'cookies-js';
import { pushPath } from 'redux-simple-router';

import cookieGet from '../services/user/cookie/get';
import profileGet from '../services/user/profile/get';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

function initialize() {
  return (dispatch) => {
    dispatch({ type: 'user:initialize:start' })

    const cookieData = cookieGet();

    if (cookieData.email) {
      profileGet(cookieData.email, function(error, profile) {
        if (error) {
          if (error.error.status === 404) {
            dispatch({ type: 'user:getProfile:success', payload: profile });
            dispatch({ type: 'user:initialize:success', payload: cookieData });

            return;

          } else {
            cookieClear();

            dispatch({ type: 'user:initialize:failure' });

            return;
          }
        }

        dispatch({ type: 'user:getProfile:success', payload: profile });
        dispatch({ type: 'user:initialize:success', payload: cookieData });
      });
    } else {
      dispatch({ type: 'user:initialize:success', payload: cookieData })
    }
  };
}

export default initialize;
