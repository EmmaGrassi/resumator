import request from 'superagent';

import employeeList from '../../services/employee/list';
import handleRequestError from '../../helpers/handleRequestError';

function list(type) {
  return (dispatch) => {
    dispatch({ type: 'employees:list:start' });

    employeeList(type, (error, result) => {
      if (error) {
        handleRequestError(error)();
        dispatch({ type: 'employees:list:failure', payload: error, error: true });
        return;
      }

      dispatch({ type: 'employees:list:success', payload: result });
    });
  };
}

export default list;
