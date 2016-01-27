import request from 'superagent';

import handleRequestError from '../../lib/handleRequestError';

function list() {
  return (dispatch) => {
    dispatch({ type: 'employees:list:start' });

    request
      .get(`/api/employees`)
      .set('Content-Type', 'application/json')
      .end((error, response) => {
        if (error) {
          dispatch({ type: 'employees:list:failure', error });

          handleRequestError(dispatch, error);

          return;
        }

        dispatch({ type: 'employees:list:success', response });
      });
  };
}

export default list;
