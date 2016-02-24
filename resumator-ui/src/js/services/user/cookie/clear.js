import cookies from 'cookies-js';

const cookiesOptions = {
  path: '/',
  domain: window.location.hostname
};

export default function clear() {

  cookies.expire('email', cookiesOptions);
  cookies.expire('name', cookiesOptions);
  cookies.expire('surname', cookiesOptions);
  cookies.expire('resumatorJWT', cookiesOptions);
}
