import _ from 'lodash';

export function get(k) {
  let v = localStorage.getItem(k);

  if (v) {
    v = JSON.parse(v);
  }

  return v;
}

export function set(k, _v) {
  let v = _v;

  if (_.isArray(v) || _.isObject(v)) {
    v = JSON.stringify(v);
  }

  localStorage.setItem(k, v);
}

export function del(k) {
  localStorage.setItem(k, null);
}
