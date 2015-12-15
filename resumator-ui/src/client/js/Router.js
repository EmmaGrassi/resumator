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

import Container from './components/public/container';
import EmployeesList from './components/public/employees/list';
import EmployeesNew from './components/public/employees/new';
import EmployeesResumeShow from './components/public/employees/resume/show';
import EmployeesShow from './components/public/employees/show';
import Home from './components/public/home';

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
          <Route path="/" component={ Container }>
            <IndexRoute component={ Home }/>

            <Route path="employees">
              <IndexRoute component={ EmployeesList }/>

              <Route path="new" component={ EmployeesNew }/>

              <Route path=":id">
                <IndexRoute component={ EmployeesShow }/>

                <Route path="resume" component={ EmployeesResumeShow }/>
              </Route>
            </Route>
          </Route>
        </ReactRouter>
      </Provider>
    );
  }

  start() {
    log.debug('Router#start');

    ReactDOM.render(this.reactRouter, this.rootElement);
  }
}
