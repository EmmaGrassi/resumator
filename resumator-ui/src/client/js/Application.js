import _ from 'lodash';
import domready from 'domready';

import promiseFromCallback from '../lib/promise/promiseFromCallback';
import Router from './Router';
import log from '../lib/log';

import actions from './actions';
import store from './store';
import reducers from './reducers';

export default class Application {
  constructor(options = {}) {
    log.debug('Application#constructor');

    this.actions = actions;
    this.reducers = reducers;

    this.store = store(this.reducers, options.store && options.store.initialState || {});

    // this.levelDBStore = levelDBStore(options.leveldb);

    this.router = new Router({
      store: this.store,
      rootElement: document.getElementById('root')
    });
  }

  handleError(error) {
    const { message, stack } = error;

    console.error(message && stack && `${message} ${stack}` || stack || error);
  }

  async domIsReady() {
    log.debug('Application#domIsReady');

    return promiseFromCallback(domready);
  }

  async startRouter() {
    log.debug('Application#startRouter');

    this.router.start();
  }

  getTokenFromCookie() {
    log.debug('Application#getTokenFromCookie');

    this.store.dispatch(this.actions.getAuthentication());
  }

  async start() {
    log.debug('Application#start');

    try {
      this.getTokenFromCookie();
      await this.domIsReady();
      await this.startRouter();
    } catch(error) {
      this.handleError(error);
    }
  }
}