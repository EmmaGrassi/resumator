import request from 'superagent';
import profileGet from '../user/profile/get';
import sanitizeEmployee from './helpers/sanitizeEmployee';
import getXSRFToken from './helpers/getXSRFToken';

export default function update(email, data, cb) {
  request
    .put(`/api/employees/${email}`)
    .send(sanitizeEmployee(data))
    .set('Content-Type', 'application/json')
    .set('X-XSRF-Token', getXSRFToken())
    .end((error, response) => {
      if (error) {
        let errMessage = '';
        try {
          const err = JSON.parse(error);
          errMessage = JSON.parse(error);
        } catch (e) {
          errMessage = error;
        }
        return cb(error, errMessage);
      }

      const { email } = JSON.parse(response.text);

      cb(null, email);
    });
}
