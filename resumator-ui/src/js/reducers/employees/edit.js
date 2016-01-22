import immutable from 'immutable';

import item from './common/item';

const defaults = immutable.Map({
  isFetching: false,

  item: item
});

function edit(state = defaults, action = {}) {
  switch (action.type) {
    case 'employees:edit:start':
      return state
        .set('isFetching', true);

    case 'employees:edit:success':
      return state
        .set('isFetching', false)
        .set('item', immutable.fromJS(action.response));

    case 'employees:edit:failure':
      return state
        .set('isFetching', false);

    default:
      return state;
  }
}

export default edit;
