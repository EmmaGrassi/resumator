import request from 'superagent';

import employeeList from '../../services/employee/list';

function list(type) {
  return (dispatch) => {
    dispatch({ type: 'employees:list:start' });

    employeeList(type, (error, result) => {
      if (error) {
        dispatch({ type: 'employees:list:failure', payload: error, error: true });
        return;
      }

      dispatch({ type: 'employees:list:success', payload: result });
    });
  };
}

export default list;
