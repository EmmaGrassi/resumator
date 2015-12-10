import { createStore, applyMiddleware } from 'redux';
import thunkMiddleware from 'redux-thunk';
import loggerMiddleware from 'redux-logger';

const store = applyMiddleware(
  thunkMiddleware
  // loggerMiddleware
)(createStore);

export default store;

// export default createStore;
