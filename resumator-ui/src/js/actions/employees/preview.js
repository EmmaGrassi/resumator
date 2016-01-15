import qwest from 'qwest';

function preview(id) {
  return (dispatch) => {
    dispatch({ type: 'employees:preview:start' });

    qwest
      .get(`/data/employees/${id}.json`)
      .then((xhr, response) => {
        dispatch({ type: 'employees:preview:success', response });
      })
      .catch((error) => {
        dispatch({ type: 'employees:preview:failure', error });
      });
  };
}

export default preview;
