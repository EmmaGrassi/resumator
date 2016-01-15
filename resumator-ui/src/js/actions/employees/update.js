import qwest from 'qwest';

function update(id) {
  return (dispatch) => {
    // TODO: implement.
    dispatch({ type: 'employees:update:start' });
    dispatch({ type: 'employees:update:success' });
  };
}

export default update;
