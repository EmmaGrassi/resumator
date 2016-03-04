import request from 'superagent';

import profileGet from '../user/profile/get';

import sanitizeEmployee from './helpers/sanitizeEmployee';

export default function update(email, data, cb) {
  request
    .put(`/api/employees/${email}`)
    .send(sanitizeEmployee(data))
    .set('Content-Type', 'application/json')
    .end((error, response) => {
      if (error) {
        return cb(error, JSON.parse(response.text).fields);
      }

      const { email } = JSON.parse(response.text);

      cb(null, email);
    });
}
