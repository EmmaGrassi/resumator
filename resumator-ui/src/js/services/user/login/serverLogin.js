import request from '../../../lib/request';

export default function post(token, cb) {
  const url = `${window.location.origin}/api/login`;

  const options = {
    headers: {
      'user-token': token,
    },
    withCredentials: true,
  };

  request.post(url, null, options)
    .then(result => {
      const response = JSON.parse(result.response);
      cb(null, response);
    })
    .catch(error => {
      cb(error);
    });
}
