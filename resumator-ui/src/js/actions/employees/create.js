import { pushPath } from 'redux-simple-router';

import employeeTypeToURL from '../../helpers/employeeTypeToURL';

import createService from '../../services/employee/create';

export default function create(data) {
  return (dispatch) => {
    dispatch({ type: 'employees:create:start' });

    data.type = data.type || 'EMPLOYEE';
    data.courses = data.courses || [];
    data.education = data.education || [];
    data.experience = data.experience || [];
    data.languages = data.languages || [];

    createService(data, (error, results) => {
      if (error) {
        dispatch({ type: 'employees:create:failure', errors: results });
        return;
      }

      // So it makes sense in the code below..
      const { email } = results;

      // TODO: Don't really want to emit this here?
      dispatch({ type: 'employees:getProfile:success', payload: results });

      dispatch({ type: 'employees:create:success', payload: email });

      dispatch(pushPath(`/${employeeTypeToURL(data.type)}/${email}/edit`));
    });
  };
}
