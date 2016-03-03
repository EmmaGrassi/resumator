import request from 'superagent';

function show(email) {
  return (dispatch) => {
    dispatch({ type: 'employees:show:start' });

    request
      .get(`/api/employees/${email}`)
      .set('Content-Type', 'application/json')
      .end((error, _response) => {
        if (error) {
          dispatch({ type: 'employees:show:failure', error });
          return;
        }

        const response = JSON.parse(_response.text);

        delete response._links;

        dispatch({ type: 'employees:show:success', response });
      });
  };
}

export default show;

/*

store
  .user
    .profile
    .session
  .employees
    .create
    .edit
    .list
    .show

*/
