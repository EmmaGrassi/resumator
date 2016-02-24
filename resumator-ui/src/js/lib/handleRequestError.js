import { pushPath } from 'redux-simple-router';
import cookies from 'cookies-js';
import cookieClear from '../services/user/cookie/clear';

function handleRequestError(dispatch, error) {
  // TODO: Other error types.
  if (error.status === 401) {
    dispatch(pushPath(`/not-authorized`));
  }
  
  else if (error.status === 403) {
	    //redirect to homepage,force to login
	    cookieClear();
	    dispatch(pushPath(`/`));
	  }
}

export default handleRequestError;
