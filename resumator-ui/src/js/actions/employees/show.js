import qwest from 'qwest';

function show(id) {
  return (dispatch) => {
    dispatch({ type: 'employees:show:start' });

    qwest
      .get(`/data/employees/${id}.json`)
      .then((xhr, response) => {
        dispatch({ type: 'employees:show:success', response });
      })
      .catch((error) => {
        dispatch({ type: 'employees:show:failure', error });
      });
  };
}

export default show;
