import immutable from 'immutable';

import item from './common/item';

const defaults = immutable.Map({
  isFetching: false,

  item
});

function show(state = defaults, action = {}) {
  switch (action.type) {
    case 'employees:show:start':
      return state
        .set('isFetching', true);

    case 'employees:show:success':
      return state
        .set('isFetching', false)
        .set('item', immutable.fromJS(action.response));

    case 'employees:show:failure':
      return state
        .set('isFetching', false);

    default:
      return state;
  }
}

export default show;
