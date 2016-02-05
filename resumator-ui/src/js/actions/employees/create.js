import request from 'superagent';
import { pushPath } from 'redux-simple-router';

import handleRequestError from '../../lib/handleRequestError';

function create(data) {
  return (dispatch) => {
    dispatch({ type: 'employees:create:start' });

    request
      .post(`/api/employees`)
      .send(data)
      .set('Content-Type', 'application/json')
      .end((error, response) => {
        if (error) {
          dispatch({ type: 'employees:create:failure', error });

          handleRequestError(dispatch, error);

          return;
        }

        const { email } = JSON.parse(response.text);

        dispatch({ type: 'employees:create:success', response });

        dispatch(pushPath(`/employees/${email}`));
      });
  };
}

export default create;
