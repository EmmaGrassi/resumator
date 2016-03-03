import request from 'superagent';

import list from './list';

function remove(type,email) {
  return (dispatch) => {
    dispatch({ type: 'employees:remove:start' });

    request
      .delete(`/api/employees/${email}`)
      .set('Content-Type', 'application/json')
      .end((error, response) => {
        if (error) {
          dispatch({ type: 'employees:remove:failure', error });
          return;
        }

        dispatch({ type: 'employees:remove:success', response });

        dispatch(list(type));
      });
  };
}

export default remove;
