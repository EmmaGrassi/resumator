const React = require('react');
const ReactDOM = require('react-dom');
const _ = require('lodash');
const reactRouter = require('react-router');
import {
  DefaultRoute,
  HistoryLocation,
  IndexRoute,
  Redirect,
  Route,
  Router as ReactRouter,
  create,
} from 'react-router';
const { Provider } = require('react-redux');

const log = require('../lib/log');

const Container = require('./components/public/container');
const EmployeesList = require('./components/public/employees/list');
const EmployeesNew = require('./components/public/employees/new');
const EmployeesResumeShow = require('./components/public/employees/resume/show');
const EmployeesShow = require('./components/public/employees/show');
const Home = require('./components/public/home');

module.exports = class Router {
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
