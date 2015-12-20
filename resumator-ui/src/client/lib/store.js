const _ = require('lodash');

function get(k) {
  let v = localStorage.getItem(k);

  if (v) {
    v = JSON.parse(v);
  }

  return v;
}

function set(k, _v) {
  let v = _v;

  if (_.isArray(v) || _.isObject(v)) {
    v = JSON.stringify(v);
  }

  localStorage.setItem(k, v);
}

function del(k) {
  localStorage.setItem(k, null);
}

module.exports = {
  get,
  set,
  del
}
