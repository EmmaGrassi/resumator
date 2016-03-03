// import { pushPath } from 'redux-simple-router';

import cookieGet from '../services/user/cookie/get';
import cookieClear from '../services/user/cookie/clear';
import profileGet from '../services/user/profile/get';

function initialize() {
  return (dispatch) => {
    dispatch({ type: 'user:initialize:start' });

    const cookieData = cookieGet();

    if (cookieData.idToken && cookieData.email) {
      profileGet(cookieData.email, (error, profile) => {
        if (error) {
          if (error.error.status === 404) {
            // TODO: Don't send getProfile actions from the initialize action?
            //       Call an initialize Service and have that service call the
            //       getprofile service instead.
            dispatch({ type: 'user:getProfile:success', payload: profile });
            dispatch({ type: 'user:initialize:success', payload: cookieData });

            return;
          }

          cookieClear();

          dispatch({ type: 'user:initialize:failure' });

          return;
        }

        // TODO: Don't send getProfile actions from the initialize action? Call
        //       an initialize Service and have that service call the getprofile
        //       service instead.
        dispatch({ type: 'user:getProfile:success', payload: profile });
        dispatch({ type: 'user:initialize:success', payload: cookieData });
      });
    } else {
      dispatch({ type: 'user:initialize:success', payload: cookieData })
    }
  };
}

export default initialize;
