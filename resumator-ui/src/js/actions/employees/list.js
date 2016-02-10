import request from 'superagent';

import employeeList from '../../services/employee/list';
import handleRequestError from '../../lib/handleRequestError';

function list() {
  return (dispatch) => {
    dispatch({ type: 'employees:list:start' });

    employeeList((error, result) => {
      if (error) {
        dispatch({ type: 'employees:list:failure', payload: error, error: true });
        return;
      }

      dispatch({ type: 'employees:list:success', payload: result });
    });
  };
}

export default list;
