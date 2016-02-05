import { pushPath } from 'redux-simple-router';

function handleRequestError(dispatch, error) {
  // TODO: Other error types.
  if (error.status === 401) {
    dispatch(pushPath(`/not-authorized`));
  }
}

export default handleRequestError;
