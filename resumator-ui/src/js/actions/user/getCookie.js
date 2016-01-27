import cookies from 'cookies-js';

import login from './login';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

function getCookie() {
  return (dispatch) => {
    const idToken = cookies.get('idToken', cookiesOptions);
    const expiresAt = cookies.get('expiresAt', cookiesOptions);
    const email = cookies.get('email', cookiesOptions);
    const name = cookies.get('name', cookiesOptions);
    const surname = cookies.get('surname', cookiesOptions);
    const imageUrl = cookies.get('imageUrl', cookiesOptions);

    if (idToken) {
      dispatch(login({
        idToken,
        expiresAt,
        email,
        name,
        surname,
        imageUrl
      }));
    }
  };
}

export default getCookie;
