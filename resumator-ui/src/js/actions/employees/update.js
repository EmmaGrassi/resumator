import { isEmpty } from 'lodash';
import { pushPath } from 'redux-simple-router';

import store from '../../store';

import employeeTypeToURL from '../../helpers/employeeTypeToURL';

import updateService from '../../services/employee/update';

import showAlert from '../alerts/show';
import hideAlert from '../alerts/hide';

export default function update(email) {
  return (dispatch) => {
    dispatch({ type: 'employees:update:start' });

    const data = store.getState().employees.edit.toJS().item;

    data.experience = data.experience.filter(x => !isEmpty(x));
    data.education = data.education.filter(x => !isEmpty(x));
    data.courses = data.courses.filter(x => !isEmpty(x));
    data.languages = data.languages.filter(x => !isEmpty(x));

    updateService(email, data, (error, results) => {
      if (error) {
        dispatch({ type: 'employees:update:failure', errors: results });
        return;
      }

      dispatch(hideAlert({ id: 'user:update' }));
      dispatch(showAlert({
        level: 'warning',
        message: `Updated user: ${email}`,
        id: 'user:updated',
      }));

      setTimeout(() => {
        dispatch(hideAlert({ id: 'user:update' }));
      }, 5000);

      dispatch({ type: 'employees:update:success', payload: results });
    });
  };
}
