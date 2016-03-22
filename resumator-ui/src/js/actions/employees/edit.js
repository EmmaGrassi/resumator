import editService from '../../services/employee/edit';
import handleRequestError from '../../helpers/handleRequestError';

export default function edit(email) {
  return (dispatch) => {
    dispatch({ type: 'employees:edit:start' });

    editService(email, (error, results) => {
      if (error) {
        handleRequestError(error)();
        dispatch({ type: 'employees:edit:failure', errors: results });
        return;
      }

      dispatch({ type: 'employees:edit:success', payload: results });
    });
  };
}
