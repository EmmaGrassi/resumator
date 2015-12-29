const domready = require('domready');

const promiseFromCallback = require('./lib/promise/promiseFromCallback');
const Router = require('./router');

const actions = require('./actions');
const store = require('./store');
const reducers = require('./reducers');

class Application {
  constructor(options = {}) {
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
    return promiseFromCallback(domready);
  }

  async startRouter() {
    this.router.start();
  }

  getTokenFromCookie() {
    this.store.dispatch(this.actions.getAuthentication());
  }

  async start() {
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
