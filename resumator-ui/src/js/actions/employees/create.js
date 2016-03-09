import { pushPath } from 'redux-simple-router';

import store from '../../store';

import employeeTypeToURL from '../../helpers/employeeTypeToURL';

import createService from '../../services/employee/create';
import profileGetService from '../../services/user/profile/get';

import showAlert from '../alerts/show';
import hideAlert from '../alerts/hide';

export default function create() {
  return (dispatch) => {
    dispatch({ type: 'employees:create:start' });

    const data = store.getState().employees.create.toJS().item;

    createService(data, (error, results) => {
      if (error) {
        dispatch({ type: 'employees:create:failure', errors: results });
        return;
      }

      // So it makes sense in the code below..
      const email = results;

      profileGetService(email, (error, results) => {
        if (error) {
          dispatch({ type: 'employees:create:failure', errors: results });
          return;
        }

        // TODO: Don't really want to emit this here?
        dispatch({ type: 'user:getProfile:success', payload: results });

        dispatch({ type: 'employees:create:success', payload: email });
        dispatch(showAlert({
          level: 'warning',
          message: `Created new user: ${email}, Please continue filling in the rest of the info.`,
          id: `user:created[${email}]`,
        }));

        setTimeout(() => {
          dispatch(hideAlert({ id: `user:created[${email}]` }));
        }, 5000);

        dispatch(pushPath(`/${employeeTypeToURL(data.type)}/${email}/edit/experience`));
      });
    });
  };
}
