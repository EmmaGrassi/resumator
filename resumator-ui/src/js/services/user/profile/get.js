import cookies from 'cookies-js';

import request from '../../../lib/request';
import handleRequestError from '../../../lib/handleRequestError';
import promiseFromNodeCallback from '../../../lib/promise/promiseFromNodeCallback';

export default function get(email, cb) {
  const url = `${window.location.origin}/api/employees/${email}`;

  request.get(`${window.location.origin}/api/employees/${email}`, null, {
    dataType: 'json',
    responsType: 'json'
  })
    .then(result => {
      const response = JSON.parse(result.response);

      delete response._links;

      cb(null, response);
    })
    .catch(error => {
      cb(error);
    });
}
