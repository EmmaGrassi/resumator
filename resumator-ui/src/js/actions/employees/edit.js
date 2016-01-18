import qwest from 'qwest';

function edit(id) {
  return (dispatch) => {
    dispatch({ type: 'employees:edit:start' });

    qwest
      .get(`/data/employees/${id}.json`, {
        dataType: 'json',
        responseType: 'json'
      })
      .then((xhr, response) => {
        dispatch({ type: 'employees:edit:success', response });
      })
      .catch((error) => {
        dispatch({ type: 'employees:edit:failure', error });
      });
  };
}

export default edit;
