import cookies from 'cookies-js';

export default function get(data) {
  const cookiesOptions = {
    path: '/',
    domain: window.location.hostname,
    secure: false
  };

  cookies.set('email', data.email, cookiesOptions);
  cookies.set('name', data.name, cookiesOptions);
  cookies.set('surname', data.surname, cookiesOptions);

}
