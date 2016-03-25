import request from 'superagent';
import getSearchStrings from '../../helpers/getSearchStrings';
const searchableKeys = ['fullName'];

export default function list(type, xsrfToken, cb) {
  request
    .get(`/api/employees?type=${type}`)
    .set('Content-Type', 'application/json')
    .set('X-XSRF-Token', xsrfToken)
    .end((error, response) => {
      if (error) {
        return cb(error);
      }

      const json = JSON.parse(response.text);
      const token = json._embedded.xsrf[0].token;

      let employees;
      if (json._embedded && json._embedded.employees) {
        employees = json._embedded.employees.map((v) => {
          delete v._links;
          v.search = getSearchStrings(v);
          return v;
        });
      } else {
        employees = [];
      }

      cb(null, employees, token);
    });
}
