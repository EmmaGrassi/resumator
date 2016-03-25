import editService from '../../services/employee/edit';
import handleRequestError from '../../helpers/handleRequestError';
import store from '../../store';
const xsrfToken = store.getState().user.session.toJS().xsrf;

export default function edit(email) {
  return (dispatch) => {
    dispatch({ type: 'employees:edit:start' });

    editService(email, xsrfToken, (error, results, token) => {
      if (error) {
        handleRequestError(error)();
        dispatch({ type: 'employees:edit:failure', errors: results });
        return;
      }

      dispatch({ type: 'user:xsrf-token:received', payload: token });
      dispatch({ type: 'employees:edit:success', payload: results });
    });
  };
}
