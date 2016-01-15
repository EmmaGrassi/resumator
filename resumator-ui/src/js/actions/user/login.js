function login(data) {
  return (dispatch) => {
    document.cookie = `resumatorJWT=${data.token}`;

    dispatch({ type: 'user:login:success', data });
  };
}

export default login;
