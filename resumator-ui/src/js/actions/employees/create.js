import { pushPath } from 'redux-simple-router';

import employeeTypeToURL from '../../helpers/employeeTypeToURL';

import createService from '../../services/employee/create';
import profileGetService from '../../services/user/profile/get';

export default function create(data) {
  return (dispatch) => {
    dispatch({ type: 'employees:create:start' });

    data.type = data.type || 'EMPLOYEE';
    data.courses = data.courses || [];
    data.education = data.education || [];
    data.experience = data.experience || [];
    data.languages = data.languages || [];

    createService(data, (error, results) => {
      debugger;

      if (error) {
        dispatch({ type: 'employees:create:failure', errors: results });
        return;
      }

      // So it makes sense in the code below..
      const email = results;

      profileGetService(email, (error, results) => {
        debugger;

        if (error) {
          dispatch({ type: 'employees:create:failure', errors: results });
          return;
        }

        // TODO: Don't really want to emit this here?
        dispatch({ type: 'user:getProfile:success', payload: results });

        dispatch({ type: 'employees:create:success', payload: email });

        dispatch(pushPath(`/${employeeTypeToURL(data.type)}/${email}/edit`));
      });
    });
  };
}
