import _ from 'lodash';

function mapper(obj) {
  if (_.isObject(obj)) {
    return Object.keys(obj).map(key => {
      const value = obj[key];
      if (_.isArray(value)) {
        return value.map(item => mapper(item));
      }
      return obj[key].toLowerCase();
    });
  }
  if (_.isArray(obj)) {
    return obj.map(x => obj[x].toLowerCase());
  }

  return obj.toLowerCase();
}

export default function getSearchStrings(item, searchableKeys) {
  // TODO: Filter out the not searchable keys
  return _.flattenDeep(mapper(item));
}
