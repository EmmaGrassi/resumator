
import clearCookie from '../services/user/cookie/clear';


function showError(msg) {
  return console.error('Bad request error', msg);
}

export default function handleRequestError(error) {
  return error.status === 403 ? clearCookie : showError;
}
