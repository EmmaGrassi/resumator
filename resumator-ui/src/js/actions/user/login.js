import cookies from 'cookies-js';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname,
  expires: Infinity,
  secure: false
};

function login(data) {
  return (dispatch) => {
    cookies.set('token', data.token, cookiesOptions);
    cookies.set('id', data.id, cookiesOptions);
    cookies.set('email', data.email, cookiesOptions);
    cookies.set('name', data.name, cookiesOptions);
    cookies.set('surname', data.surname, cookiesOptions);
    cookies.set('imageUrl', data.imageUrl, cookiesOptions);

    cookies.set('resumatorJWT', data.token, cookiesOptions);

    dispatch({ type: 'user:login:success', data });
  };
}

export default login;
