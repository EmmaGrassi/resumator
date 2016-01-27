import request from 'superagent';

import list from './list';

function show(id) {
  return (dispatch) => {
    dispatch({ type: 'employees:show:start' });

    request
      .get(`/api/employees/${id}`)
      .set('Content-Type', 'application/json')
      .end((error, _response) => {
        if (error) {
          dispatch({ type: 'employees:show:failure', error });

          handleRequestError(dispatch, error);

          return;
        }

        const response = JSON.parse(_response.text);

        delete response._links;

        dispatch({ type: 'employees:show:success', response });
      });
  };
}

export default show;
