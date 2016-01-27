import cookies from 'cookies-js';

function login(data) {
  return (dispatch) => {
    const cookiesOptions = {
      path: '/',
      domain: window.location.hostname,
      expires: new Date(+data.expiresAt),
      secure: false
    };

    cookies.set('idToken', data.idToken, cookiesOptions);
    cookies.set('expiresAt', data.expiresAt, cookiesOptions);
    cookies.set('email', data.email, cookiesOptions);
    cookies.set('name', data.name, cookiesOptions);
    cookies.set('surname', data.surname, cookiesOptions);
    cookies.set('imageUrl', data.imageUrl, cookiesOptions);

    cookies.set('resumatorJWT', data.idToken, cookiesOptions);

    dispatch({ type: 'user:login:success', data });
  };
}

export default login;
