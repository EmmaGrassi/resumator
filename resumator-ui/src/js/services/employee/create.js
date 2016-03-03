import request from 'superagent';

import profileGet from '../user/profile/get';

export default function create(data, cb) {
  data.type = data.type || 'EMPLOYEE';
  data.courses = data.courses || [];
  data.education = data.education || [];
  data.experience = data.experience || [];
  data.languages = data.languages || [];

  request
    .post('/api/employees')
    .send(data)
    .set('Content-Type', 'application/json')
    .end((error, response) => {
      if (error) {
        return cb(error, JSON.parse(response.text).fields);
      }

      const { email } = JSON.parse(response.text);

      profileGet(email, (error, results) => {
        if (error) {
          return cb(error);
        }

        // Don't care about these results here, those will be picked up by the
        // correct reducer
        return cb(null, results);
      });
    });
}
