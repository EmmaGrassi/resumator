import qwest from 'qwest';

function show(id) {
  return (dispatch) => {
    dispatch({ type: 'employees:show:start' });

    qwest
      .get(`/api/employees/${id}`, {
        dataType: 'json',
        responseType: 'hal+json'
      })
      .then((xhr, _response) => {
        const response = JSON.parse(_response);

        delete response._links;

        dispatch({ type: 'employees:show:success', response });
      })
      .catch((error) => {
        dispatch({ type: 'employees:show:failure', error });
      });
  };
}

export default show;
