import cookies from 'cookies-js';

import request from '../../../lib/request';
import handleRequestError from '../../../lib/handleRequestError';
import promiseFromNodeCallback from '../../../lib/promise/promiseFromNodeCallback';

export default function post(token, cb) {
  const url = `${window.location.origin}/api/login`;

  var options = {

		  headers : { "user-token" :token },
  		  withCredentials:true
        };

  request.post(url, null,options)
    .then(result => { 

      const response = JSON.parse(result.response);
      cb(null, response);
      
    })
    .catch(error => {
      cb(error);
    });
  
}