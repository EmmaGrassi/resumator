import request from 'superagent';

import handleRequestError from '../../lib/handleRequestError';
import list from './list';

function remove(email) {
  return (dispatch) => {
    dispatch({ type: 'employees:remove:start' });

    request
      .delete(`/api/employees/${email}`)
      .set('Content-Type', 'application/json')
      .end((error, response) => {
        if (error) {
          dispatch({ type: 'employees:remove:failure', error });

          handleRequestError(dispatch, error);

          return;
        }

        dispatch({ type: 'employees:remove:success', response });

        dispatch(list());
      });
  };
}

export default remove;
