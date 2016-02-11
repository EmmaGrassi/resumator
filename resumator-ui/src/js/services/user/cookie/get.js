import cookies from 'cookies-js';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

export default function get() {
  const idToken = cookies.get('idToken', cookiesOptions);
  const expiresAt = cookies.get('expiresAt', cookiesOptions);
  const email = cookies.get('email', cookiesOptions);
  const name = cookies.get('name', cookiesOptions);
  const surname = cookies.get('surname', cookiesOptions);
  const imageUrl = cookies.get('imageUrl', cookiesOptions);

  return {
    idToken,
    expiresAt,
    email,
    name,
    surname,
    imageUrl
  }
}
