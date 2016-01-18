import qwest from 'qwest';
import { pushPath } from 'redux-simple-router';

function create(data) {
  return (dispatch) => {
    dispatch({ type: 'employees:create:start' });

    qwest
      .post(`/api/employees`, data, {
        dataType: 'json',
        responseType: 'json'
      })
      .then((xhr, response) => {
        const id = response.id;

        dispatch({ type: 'employees:create:success', response });

        dispatch(pushPath(`/employees/${id}`));
      })
      .catch((error) => {
        dispatch({ type: 'employees:create:failure', error });
      });
  };
}

export default create;
