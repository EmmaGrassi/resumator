import request from 'superagent';

import employeeList from '../../services/employee/list';
import handleRequestError from '../../helpers/handleRequestError';
import store from '../../store';
const xsrfToken = store.getState().user.session.toJS().xsrf;

function list(type) {
  return (dispatch) => {
    dispatch({ type: 'employees:list:start' });
    employeeList(type, xsrfToken, (error, result, token) => {
      if (error) {
        handleRequestError(error)();
        dispatch({ type: 'employees:list:failure', payload: error, error: true });
        return;
      }

      dispatch({ type: 'user:xsrf-token:received', payload: token });
      dispatch({ type: 'employees:list:success', payload: result });
    });
  };
}

export default list;
