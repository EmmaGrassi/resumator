import { combineReducers } from 'redux';

import create from './create';
import edit from './edit';
import list from './list';
import show from './show';

export default combineReducers({
  create,
  edit,
  list,
  show
});
