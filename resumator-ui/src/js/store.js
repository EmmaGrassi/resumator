import loggerMiddleware from 'redux-logger';
import thunkMiddleware from 'redux-thunk';
import { createStore, combineReducers, applyMiddleware, compose } from 'redux';
import { routeReducer } from 'redux-simple-router';

import reducers from './reducers';

const reducer = combineReducers(Object.assign({}, reducers, {
  routing: routeReducer,
}));

const store = compose(
  applyMiddleware(
    thunkMiddleware,
    loggerMiddleware({
      collapsed: true,
    })
  )
)(createStore)(reducer);

export default store;
