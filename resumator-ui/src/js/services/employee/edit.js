import request from 'superagent';

export default function edit(email, cb) {
  request
    .get(`/api/employees/${email}`)
    .set('Content-Type', 'application/json')
    .end((error, response) => {
      if (error) {
        return cb(error);
      }

      const json = JSON.parse(response.text);

      delete json._links;

      cb(null, json);
    });
}
