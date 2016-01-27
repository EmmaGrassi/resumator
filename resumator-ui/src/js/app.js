import 'babel-polyfill';

import React from 'react';
import ReactDOM from 'react-dom';
import createHistory from 'history/lib/createHashHistory';
import domready from 'domready';
import { IndexRoute, Route, Router } from 'react-router';
import { Provider } from 'react-redux';
import { syncReduxAndRouter } from 'redux-simple-router';

// Redux
import actions from './actions';
import store from './store';

// Components
import AdminContainer from './components/admin/container';
import AdminEmployeesEdit from './components/admin/employees/edit';
import AdminEmployeesList from './components/admin/employees/list';
import AdminEmployeesShow from './components/admin/employees/show';
import AdminHome from './components/admin/home';
import PublicContainer from './components/public/container';
import PublicHome from './components/public/home';
import PublicEmployeesCreate from './components/public/employees/create';
import PublicEmployeesEdit from './components/public/employees/edit';
import PublicEmployeesList from './components/public/employees/list';
import PublicEmployeesShow from './components/public/employees/show';

const rootElement = document.getElementById('root');

const history = createHistory({
  queryKey: false
});

syncReduxAndRouter(history, store);

function getRouteAttribute(routes, attribute) {
  for (let i = routes.length - 1; i >= 0; i--) {
    if (routes[i][attribute] !== undefined) {
      return routes[i][attribute];
    }
  }
}

function handleEnter(nextState, replaceState) {
  const requireAuth = getRouteAttribute(nextState.routes, 'requireAuth');

  const idToken = store.getState().user.get('idToken');

  if (requireAuth && !idToken) {
    replaceState(null, '/');
    return;
  }
}

const router = (
  <Provider store={store}>
    <Router history={history}>
      <Route path="admin" component={ AdminContainer } onEnter={handleEnter} requireAuth={true}>
        <IndexRoute component={ AdminHome } onEnter={handleEnter}/>

        <Route path="employees" component={ AdminEmployeesList } onEnter={handleEnter}>
          <Route path=":userId" component={ AdminEmployeesShow } onEnter={handleEnter}>
            <Route path="edit" component={ AdminEmployeesEdit } onEnter={handleEnter}/>
          </Route>
        </Route>
      </Route>

      <Route path="/" component={ PublicContainer } onEnter={handleEnter}>
        <IndexRoute component={ PublicHome } onEnter={handleEnter}/>

        <Route path="employees" requireAuth={true}>
          <IndexRoute component={ PublicEmployeesList } onEnter={handleEnter}/>

          <Route path="new" component={ PublicEmployeesCreate } onEnter={handleEnter}/>

          <Route path=":userId">
            <IndexRoute component={ PublicEmployeesShow } onEnter={handleEnter}/>

            <Route path="edit" component={ PublicEmployeesEdit } onEnter={handleEnter}/>
          </Route>
        </Route>
      </Route>
    </Router>
  </Provider>
);

// Load auth2 into the gapi object.
gapi.load('auth2', () => {
  window.googleAuth = gapi.auth2.init({
    client_id: '49560145160-80v99olfohmo0etbo6hugpo337p5d1nl.apps.googleusercontent.com',
    hosted_domain: 'sytac.io'
  });

  // Make sure the cookie information is stored in the store.
  store.dispatch(actions.user.getCookie());

  // When the DOM is ready, render to it.
  domready(() => {
    ReactDOM.render(router, rootElement);
  });
});

