import editService from '../../services/employee/edit';

export default function edit(email) {
  return (dispatch) => {
    dispatch({ type: 'employees:edit:start' });

    debugger;

    editService(email, (error, results) => {
      if (error) {
        dispatch({ type: 'employees:edit:failure', errors: results });
        return;
      }

      debugger;

      dispatch({ type: 'employees:edit:success', payload: results });
    });
  };
}
