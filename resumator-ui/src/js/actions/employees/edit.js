function edit(data) {
  return (dispatch) => {
    dispatch({ type: 'employees:edit', data });
  };
}

export default edit;
