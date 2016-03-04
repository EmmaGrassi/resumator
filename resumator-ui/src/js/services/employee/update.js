import request from 'superagent';

import profileGet from '../user/profile/get';

export default function update(email, data, cb) {
  data.type = data.type || 'EMPLOYEE';
  data.courses = data.courses || [];
  data.education = data.education || [];
  data.experience = data.experience || [];
  data.languages = data.languages || [];

  request
    .put(`/api/employees/${email}`)
    .send(data)
    .set('Content-Type', 'application/json')
    .end((error, response) => {
      if (error) {
        return cb(error, JSON.parse(response.text).fields);
      }

      const { email } = JSON.parse(response.text);

      cb(null, email);
    });
}
