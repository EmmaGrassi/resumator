import cookies from 'cookies-js';

import login from './login';

const cookiesOptions = {
  path: '/',
  domain: 'resumator.sytac.io'
};

function getCookie() {
  return (dispatch) => {
    const token = cookies.get('token', cookiesOptions);
    const id = cookies.get('id', cookiesOptions);
    const email = cookies.get('email', cookiesOptions);
    const name = cookies.get('name', cookiesOptions);
    const surname = cookies.get('surname', cookiesOptions);
    const imageUrl = cookies.get('imageUrl', cookiesOptions);

    if (token) {
      dispatch(login({
        token,
        id,
        email,
        name,
        surname,
        imageUrl
      }));
    }
  };
}

export default getCookie;
