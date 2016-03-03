import request from '../../../lib/request';

export default function get(email, cb) {
  request.get(`${window.location.origin}/api/employees/${email}`, null, {
    dataType: 'json',
    responsType: 'json',
  })
    .then(result => {
      const response = JSON.parse(result.response);

      delete response._links;

      cb(null, response);
    })
    .catch(error => {
      cb(error);
    });
}
