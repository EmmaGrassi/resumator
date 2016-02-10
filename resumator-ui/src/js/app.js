import 'babel-polyfill';

import React from 'react';
import ReactDOM from 'react-dom';
import cookies from 'cookies-js';
import createHistory from 'history/lib/createHashHistory';
import domready from 'domready';
import { IndexRoute, Route, Router } from 'react-router';
import { Provider } from 'react-redux';
import { syncReduxAndRouter } from 'redux-simple-router';

// Redux
import initialize from './actions/initialize';
import store from './store';

// Components
import NotAuthorized from './components/NotAuthorized';
import NotFound from './components/NotFound';
import PublicContainer from './components/public/container';
import PublicEmployeesCreate from './components/public/employees/create';
import PublicEmployeesEdit from './components/public/employees/edit';
import PublicEmployeesList from './components/public/employees/list';
import PublicEmployeesShow from './components/public/employees/show';
import PublicHome from './components/public/home';

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

// Returns true if the user has a session.
function hasSession() {
  // This needs to be taken from the cookie directly, not the Redux store.
  const idToken = cookies.get('idToken');

  return !!idToken;
}

// TODO: Implement.
function hasOwnProfile(nextState) {
  return false;
}

// TODO: Implement.
function hasAdmin(nextState) {
  return false;
}

// Gets run on every route enter.
function handleEnter(nextState, replaceState) {
  const requireSession = getRouteAttribute(nextState.routes, 'requireSession');

  if (requireSession && !hasSession(nextState)) {
    replaceState(null, '/');
    return;
  }

  const requireOwnProfile = getRouteAttribute(nextState.routes, 'requireOwnProfile');

  if (requireOwnProfile && !hasOwnProfile(nextState)) {
    replaceState(null, '/');
    return;
  }

  const requireAdmin = getRouteAttribute(nextState.routes, 'requireAdmin');

  if (requireAdmin && !hasAdmin(nextState)) {
    replaceState(null, '/');
    return;
  }
}

const router = (
  <Provider store={store}>
    <Router history={history}>
      <Route path="/" component={ PublicContainer } onEnter={handleEnter}>
        <IndexRoute component={ PublicHome } onEnter={handleEnter}/>

        <Route path="employees" requireSession={true}>
          <IndexRoute component={ PublicEmployeesList } onEnter={handleEnter}/>

          <Route path="new" component={ PublicEmployeesCreate } onEnter={handleEnter}/>

          <Route path=":userId">
            <IndexRoute component={ PublicEmployeesShow } onEnter={handleEnter}/>

            <Route path="edit" component={ PublicEmployeesEdit } onEnter={handleEnter}/>
          </Route>
        </Route>

        <Route path="not-authorized" component={NotAuthorized} />
        <Route path="not-found" component={NotFound} />
        <Route path="**" component={NotFound} />
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

  store.dispatch(initialize());

  // When the DOM is ready, render to it.
  domready(() => {
    ReactDOM.render(router, rootElement);
  });
});

