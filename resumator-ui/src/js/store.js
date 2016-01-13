import { createStore, applyMiddleware } from 'redux';
import loggerMiddleware from 'redux-logger';
import promiseMiddleware from 'redux-promise';
import thunkMiddleware from 'redux-thunk';

const store = applyMiddleware(
  thunkMiddleware,
  promiseMiddleware,
  loggerMiddleware()
)(createStore);

export default store;
