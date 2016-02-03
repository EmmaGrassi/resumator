import request from 'superagent';

import handleRequestError from '../../lib/handleRequestError';
import list from './list';

function edit(email) {
  return (dispatch) => {
    dispatch({ type: 'employees:edit:start' });

    request
      .get(`/api/employees/${email}`)
      .set('Content-Type', 'application/json')
      .end((error, _response) => {
        if (error) {
          dispatch({ type: 'employees:edit:failure', error });

          handleRequestError(dispatch, error);

          return;
        }

        const response = JSON.parse(_response.text);

        delete response._links;

        dispatch({ type: 'employees:edit:success', response });
      });
  };
}

export default edit;

