
import clearCookie from '../services/user/cookie/clear';

export default function handleRequestError(error) {
  return error.status === 403 ? clearCookie : console.error;
}
