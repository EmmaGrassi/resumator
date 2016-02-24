import cookies from 'cookies-js';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

export default function get() {

  const email = cookies.get('email', cookiesOptions);
  const name = cookies.get('name', cookiesOptions);
  const surname = cookies.get('surname', cookiesOptions);
  const idToken = cookies.get('resumatorJWT', cookiesOptions);

  return {
   idToken,
    email,
    name,
    surname
  }
}
