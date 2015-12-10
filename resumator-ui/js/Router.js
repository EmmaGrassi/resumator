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

import log from 'loglevel';

import BreadcrumbsComponent from './components/BreadcrumbsComponent';

import PublicContainerComponent from './components/public/PublicContainerComponent';
import PublicAboutPageComponent from './components/public/PublicAboutPageComponent';
import PublicContactPageComponent from './components/public/PublicContactPageComponent';
import PublicHomePageComponent from './components/public/PublicHomePageComponent';
import PublicLoginPageComponent from './components/public/PublicLoginPageComponent';
import PublicLogoutPageComponent from './components/public/PublicLogoutPageComponent';

import CMSContainerComponent from './components/cms/CMSContainerComponent';
import CMSDashboardPageComponent from './components/cms/CMSDashboardPageComponent';
import CMSUsersDestroyPageComponent from './components/cms/users/destroy/DestroyPageComponent';
import CMSUsersEditPageComponent from './components/cms/users/edit/CMSUsersEditPageComponent';
import CMSUsersListPageComponent from './components/cms/users/list/CMSUsersListPageComponent';
import CMSUsersNewPageComponent from './components/cms/users/new/NewPageComponent';
import CMSUsersShowPageComponent from './components/cms/users/show/ShowPageComponent';

export default class Router {
  constructor(options) {
    log.debug('Router#constructor');

    this.store = options.store;
    this.rootElement = options.rootElement || document.body;

    this.reactRouter = this.getRouter();
  }

  getRouter() {
    // /
    // /about
    // /contact

    // /login
    // /logout

    // /cms
    // /cms/users
    // /cms/users/:id
    // /cms/users/:id/edit

    return (
      <Provider store={this.store}>
        <ReactRouter>
          <Route path="/" component={ PublicContainerComponent }>
            <IndexRoute component={ PublicHomePageComponent }/>
            <Route path="about" components={ PublicAboutPageComponent }/>
            <Route path="contact" components={ PublicContactPageComponent }/>
            <Route path="login" components={ PublicLoginPageComponent }/>
            <Route path="logout" components={ PublicLogoutPageComponent }/>
          </Route>

          <Route path="cms" component={ CMSContainerComponent }>
            <IndexRoute component={ CMSDashboardPageComponent }/>

            <Route path="users">
              <IndexRoute component={ CMSUsersListPageComponent }/>
              <Route path="new" component={ CMSUsersNewPageComponent }/>
              <Route path=":id" component={ CMSUsersShowPageComponent }/>
              <Route path=":id/edit" component={ CMSUsersEditPageComponent }/>
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
