const { createStore, applyMiddleware } = require('redux');
const loggerMiddleware = require('redux-logger');
const promiseMiddleware = require('redux-promise');
const thunkMiddleware = require('redux-thunk');

const store = applyMiddleware(
  thunkMiddleware,
  promiseMiddleware,
  loggerMiddleware()
)(createStore);

module.exports = store;
