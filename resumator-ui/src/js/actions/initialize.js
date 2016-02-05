import cookies from 'cookies-js';

import cookieGet from '../services/user/cookie/get';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

function initialize() {
  return async (dispatch) => {
    dispatch({ type: 'user:initialize:start' })

    const payload = await cookieGet();

    dispatch({ type: 'user:initialize:success', payload });
  };
}

export default initialize;
