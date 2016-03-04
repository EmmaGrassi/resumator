import cookies from 'cookies-js';
import { pushPath } from 'redux-simple-router';

import cookieClear from '../../services/user/cookie/clear';

// TODO: Sign out with google first.
export default function logout(data) {
  return (dispatch) => {
    dispatch({ type: 'user:logout:start' })

    cookieClear();

    dispatch({ type: 'user:logout:success', data });

    dispatch(pushPath(`/`));
  };
}
