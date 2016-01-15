function remove(id) {
  return (dispatch) => {
    dispatch({ type: 'employees:remove', id });
  };
}

export default remove;
