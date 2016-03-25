import request from 'superagent';
import moment from 'moment';

export default function edit(email, token, cb) {
  request
    .get(`/api/employees/${email}`)
    .set('Content-Type', 'application/json')
    .set('X-XSRF-Token', token)
    .end((error, response) => {
      if (error) {
        return cb(error);
      }

      const json = JSON.parse(response.text);
      const token = json._embedded.xsrf[0].token;

      delete json._links;

      json.experience = json.experience.map(exp => {
        if (!exp.endDate) {
          exp.currentlyWorkHere = true;
        }
        return exp;
      });

      cb(null, json, token);
    });
}
