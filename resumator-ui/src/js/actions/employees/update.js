import { pushPath } from 'redux-simple-router';

import employeeTypeToURL from '../../helpers/employeeTypeToURL';

import updateService from '../../services/employee/update';

export default function update(email, data) {
  return (dispatch) => {
    dispatch({ type: 'employees:update:start' });

    updateService(email, data, (error, results) => {
      if (error) {
        dispatch({ type: 'employees:update:failure', errors: results });
        return;
      }

      dispatch({ type: 'employees:update:success', payload: results });
    });
  };
}
