import request from 'superagent';

import list from './list';

function edit(id) {
  return (dispatch) => {
    dispatch({ type: 'employees:edit:start' });

    request
      .get(`/api/employees/${id}`)
      .set('Content-Type', 'application/json')
      .end((error, _response) => {
        if (error) {
          dispatch({ type: 'employees:edit:failure', error });
          return;
        }

        const response = JSON.parse(_response.text);

        delete response._links;

        dispatch({ type: 'employees:edit:success', response });
      });
  };
}

export default edit;

