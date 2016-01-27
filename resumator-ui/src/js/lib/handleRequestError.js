import { pushPath } from 'redux-simple-router';

// TODO: Kind of ugly to have a path from a lib to an app file instead of the
//       other way around.
import clearCookie from '../actions/user/clearCookie';

function handleRequestError(dispatch, error) {
  // TODO: Other error types.
  if (error.status === 401) {
    dispatch(clearCookie());
    dispatch(pushPath(`/not-authorized`));
  }
}

export default handleRequestError;
