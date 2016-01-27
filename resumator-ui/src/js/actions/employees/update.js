export default update;

import request from 'superagent';
import { pushPath } from 'redux-simple-router';

function update(id, data) {
  return (dispatch) => {
    dispatch({ type: 'employees:update:start' });

    request
      .put(`/api/employees/${id}`)
      .send(data)
      .set('Content-Type', 'application/json')
      .end((error, response) => {
        if (error) {
          dispatch({ type: 'employees:update:failure', error });

          handleRequestError(dispatch, error);

          return;
        }

        dispatch({ type: 'employees:update:success', response });

        dispatch(pushPath(`/employees/${id}`));
      });
  };
}

export default update;
