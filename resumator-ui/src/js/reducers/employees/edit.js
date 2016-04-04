import immutable from 'immutable';
import { isNumber } from 'lodash';

import employee from '../common/employee';
import parseErrorFields from '../../helpers/parseErrorFields';

const defaults = immutable.Map({
  isFetching: false,
  isSaving: false,
  hasFailed: false,
  email: null,
  item: employee,
  errors: immutable.Map(),
});


function edit(state = defaults, action = {}) {
  switch (action.type) {
    case 'employees:edit:start':
      return state
        .set('isFetching', true);

    case 'employees:edit:cancel':
      return state;

    case 'employees:edit:success':
      return state
        .set('isFetching', false)
        .set('item', immutable.fromJS(action.payload));

    case 'employees:edit:failure':
      return state
        .set('isFetching', false)
        .set('hasFailed', true)
        .set('errors', action.errors.fields);

    case 'employees:edit:change':
      return state
        .setIn(['item', action.payload.key], immutable.fromJS(action.payload.value));

    case 'employees:update:start':
      return state
        .set('isSaving', true);

    case 'employees:update:success':
      return state
        .set('isSaving', false)
        .set('hasFailed', false)
        .set('errors', null);

    case 'employees:update:failure':
      return state
        .set('isSaving', false)
        .set('hasFailed', true)
        .set('errors', parseErrorFields(action.errors.fields, state));

    case 'employees:addEntry':
      const type = action.payload;
      const result = state
        .getIn(['item', type]);
      return state
        .setIn(['item', type], result.push({}));

    case 'employees:removeEntry':
      const tmpState = state.toJS();
      tmpState.item[action.payload.type].splice(action.payload.key, 1);
      const newState = immutable.fromJS(tmpState);
      return newState;

    default:
      return state;
  }
}


export default edit;
