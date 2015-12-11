import React from 'react';
import ReactDOM from 'react-dom';
import _ from 'lodash';
import reactRouter from 'react-router';
import {
  DefaultRoute,
  HistoryLocation,
  IndexRoute,
  Redirect,
  Route,
  Router as ReactRouter,
  create,
} from 'react-router';
import { Provider } from 'react-redux';

import log from '../lib/log';

import PublicContainerComponent from './components/public/PublicContainerComponent';
import PublicEmployeesIndexPageComponent from './components/public/PublicEmployeesIndexPageComponent';
import PublicEmployeesNewPageComponent from './components/public/PublicEmployeesNewPageComponent';
import PublicEmployeesResumeIndexPageComponent from './components/public/PublicEmployeesResumeIndexPageComponent';
import PublicEmployeesShowPageComponent from './components/public/PublicEmployeesShowPageComponent';
import PublicHomePageComponent from './components/public/PublicHomePageComponent';

export default class Router {
  constructor(options) {
    log.debug('Router#constructor');

    this.store = options.store;
    this.rootElement = options.rootElement || document.body;

    this.reactRouter = this.getRouter();
  }

  getRouter() {
    return (
      <Provider store={this.store}>
        <ReactRouter>
          <Route path="/" component={ PublicContainerComponent }>
            <IndexRoute component={ PublicHomePageComponent }/>

            <Route path="employees">
              <IndexRoute component={ PublicEmployeesIndexPageComponent }/>

              <Route path="new" component={ PublicEmployeesNewPageComponent }/>

              <Route path=":id">
                <IndexRoute component={ PublicEmployeesShowPageComponent }/>

                <Route path="resume" component={ PublicEmployeesResumeIndexPageComponent }/>
              </Route>
            </Route>
          </Route>
        </ReactRouter>
      </Provider>
    );
  }

  handleError(error) {
    log.error(error.stack || error.message || error);
  }

  start() {
    log.debug('Router#start');

    try {
      ReactDOM.render(this.reactRouter, this.rootElement);
    } catch(error) {
      this.handleError(error);
    }
  }
}
