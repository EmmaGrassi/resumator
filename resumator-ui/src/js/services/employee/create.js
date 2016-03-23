import request from 'superagent';
import profileGet from '../user/profile/get';
import sanitizeEmployee from './helpers/sanitizeEmployee';
import getXSRFToken from './helpers/getXSRFToken';


export default function create(data, cb) {
  request
    .post('/api/employees')
    .send(sanitizeEmployee(data))
    .set('Content-Type', 'application/json')
    .set('X-XSRF-Token', getXSRFToken())
    .end((error, response) => {
      if (error) {
        return cb(error, JSON.parse(response.text).fields);
      }

      const { email } = JSON.parse(response.text);

      cb(null, email);
    });
}
