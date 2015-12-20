const { createStore, applyMiddleware } = require('redux');
const thunkMiddleware = require('redux-thunk');
const loggerMiddleware = require('redux-logger');

const store = applyMiddleware(
  thunkMiddleware
  // loggerMiddleware
)(createStore);

module.exports = store;

// module.exports = createStore;
