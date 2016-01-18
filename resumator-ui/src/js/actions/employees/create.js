import qwest from 'qwest';

function create(data) {
  return (dispatch) => {
    dispatch({ type: 'employees:create:start' });

    qwest
      .post(`/api/employee`, data, {
        dataType: 'json',
        responseType: 'json'
      })
      .then((xhr, response) => {
        debugger;
        dispatch({ type: 'employees:create:success', response });
      })
      .catch((error) => {
        debugger;
        dispatch({ type: 'employees:create:failure', error });
      });
  };
}

export default create;
