import request from 'superagent';

import list from './list';
import store from '../../store';

function remove(type, email) {
  return (dispatch) => {
    dispatch({ type: 'employees:remove:start' });
    const xsrfToken = store.getState().user.session.toJS().xsrf;

    request
      .delete(`/api/employees/${email}`)
      .set('Content-Type', 'application/json')
      .set('X-XSRF-Token', xsrfToken)
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
