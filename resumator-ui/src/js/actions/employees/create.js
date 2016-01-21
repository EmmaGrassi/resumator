import request from 'superagent';
import { pushPath } from 'redux-simple-router';

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
          return;
        }

        const { id } = JSON.parse(response.text);

        dispatch({ type: 'employees:create:success', response });

        dispatch(pushPath(`/employees/${id}`));
      });
  };
}

export default create;
