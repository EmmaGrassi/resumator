import qwest from 'qwest';

function list(data) {
  return (dispatch) => {
    dispatch({ type: 'employees:list:start' });

    qwest
      .get(`/api/employees`, {
        dataType: 'json',
        responseType: 'json'
      })
      .then((xhr, response) => {
        dispatch({ type: 'employees:list:success', response });
      })
      .catch((error) => {
        dispatch({ type: 'employees:list:failure', error });
      });
  };
}

export default list;
