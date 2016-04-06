import request from 'superagent';
import profileGet from '../user/profile/get';
import sanitizeEmployee from './helpers/sanitizeEmployee';

export default function update(email, data, token, cb) {
  request
    .put(`/api/employees/${email}`)
    .send(sanitizeEmployee(data))
    .set('Content-Type', 'application/json')
    .set('X-XSRF-Token', token)
    .end((error, response) => {
      if (error) {
        let errMessage = '';
        try {
          errMessage = JSON.parse(error.response.text);
        } catch (e) {
          errMessage = error.response.text;
        }
        return cb(error, errMessage);
      }

      const { email } = JSON.parse(response.text);

      cb(null, email);
    });
}
