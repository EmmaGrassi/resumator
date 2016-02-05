import cookies from 'cookies-js';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

export default async function clear() {
  cookies.expire('idToken', cookiesOptions);
  cookies.expire('expiresAt', cookiesOptions);
  cookies.expire('email', cookiesOptions);
  cookies.expire('name', cookiesOptions);
  cookies.expire('surname', cookiesOptions);
  cookies.expire('imageUrl', cookiesOptions);
  cookies.expire('resumatorJWT', cookiesOptions);
}
