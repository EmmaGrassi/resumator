function logout(data) {
  return (dispatch) => {
    document.cookie = '';

    dispatch({ type: 'user:logout:success', data });
  };
}

export default logout;
