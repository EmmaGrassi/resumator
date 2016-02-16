import request from 'superagent';

export default function list(type, cb) {
  request
    .get(`/api/employees?type=${type}`)
    .set('Content-Type', 'application/json')
    .end((error, response) => {
      if (error) {
        return cb(error);
      }

      const json = JSON.parse(response.text);

      let employees;
      if (json._embedded && json._embedded.employees) {
        employees = json._embedded.employees.map((v) => {
          delete v._links;

          return v;
        });
      } else {
        employees = [];
      }

      cb(null, employees);
    });
}
