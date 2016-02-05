import request from 'superagent';
import { pushPath } from 'redux-simple-router';

import handleRequestError from '../../lib/handleRequestError';

function update(email, data) {
  return (dispatch) => {
    dispatch({ type: 'employees:update:start' });

    request
      .put(`/api/employees/${email}`)
      .send(data)
      .set('Content-Type', 'application/json')
      .end((error, response) => {
        if (error) {
          dispatch({ type: 'employees:update:failure', error });

          handleRequestError(dispatch, error);

          return;
        }

        dispatch({ type: 'employees:update:success', response });

        dispatch(pushPath(`/employees/${email}`));
      });
  };
}

export default update;
