function create(data) {
  return (dispatch) => {
    dispatch({ type: 'employees:create', data });
  };
}

export default create;
