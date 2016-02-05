import cookies from 'cookies-js';

import request from '../../../lib/request';
import handleRequestError from '../../../lib/handleRequestError';
import promiseFromNodeCallback from '../../../lib/promise/promiseFromNodeCallback';

export default async function get(email) {
  try {
    const url = `${window.location.origin}/api/employees/${email}`;

    const result = await request.get(`${window.location.origin}/api/employees/${email}`, null, {
      dataType: 'json',
      responsType: 'json'
    });

    debugger;

    const response = JSON.parse(_response.text);

    delete response._links;

    return response;
  } catch (error) {
    throw error;
  }
}
