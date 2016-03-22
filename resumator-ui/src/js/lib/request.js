import qwest from 'qwest';

function get(url, data, options, before) {
  return new Promise((resolve, reject) => {
    qwest.get(url, data, options, before)
      .then((xhr, response) => {
        resolve({ xhr, response });
      })
      .catch((error, xhr, response) => {
        reject({ error, xhr, response });
      });
  });
}

function post(url, data, options, before) {
  return new Promise((resolve, reject) => {
    qwest.post(url, data, options, before)
      .then((xhr, response) => {
        resolve({ xhr, response });
      })
      .catch((error, xhr, response) => {
        reject({ error, xhr, response });
      });
  });
}

function put(url, data, options, before) {
  return new Promise((resolve, reject) => {
    qwest.put(url, data, options, before)
      .then((xhr, response) => {
        resolve({ xhr, response });
      })
      .catch((error, xhr, response) => {
        reject({ error, xhr, response });
      });
  });
}

function _delete(url, data, options, before) {
  return new Promise((resolve, reject) => {
    qwest.delete(url, data, options, before)
      .then((xhr, response) => {
        resolve({ xhr, response });
      })
      .catch((error, xhr, response) => {
        reject({ error, xhr, response });
      });
  });
}

export default {
  get,
  post,
  put,
  delete: _delete,
};
