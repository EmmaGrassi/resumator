import { combineReducers } from 'redux';

import profile from './profile';
import session from './session';

export default combineReducers({
  profile,
  session
});
