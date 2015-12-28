const _ = require('lodash');
const domready = require('domready');
const Cookies = require('cookies-js');

const promiseFromCallback = require('../lib/promise/promiseFromCallback');
const Router = require('./Router');
const log = require('../lib/log');

const actions = require('./actions');
const store = require('./store');
const reducers = require('./reducers');

class Application {
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

    Cookies.set('resumator-JWT', 'value', { expires: Infinity });

    try {
      this.getTokenFromCookie();
      await this.domIsReady();
      await this.startRouter();
    } catch(error) {
      this.handleError(error);
    }
  }
}

module.exports = Application;
