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
import PublicFreelancersCreate from './components/public/freelancers/create';
import PublicFreelancersEdit from './components/public/freelancers/edit';
import PublicFreelancersList from './components/public/freelancers/list';
import PublicFreelancersShow from './components/public/freelancers/show';
import PublicHome from './components/public/home';
import PublicProspectsCreate from './components/public/prospects/create';
import PublicProspectsEdit from './components/public/prospects/edit';
import PublicProspectsList from './components/public/prospects/list';
import PublicProspectsShow from './components/public/prospects/show';

const rootElement = document.getElementById('root');

const history = createHistory({
  queryKey: true,
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
  const idToken = cookies.get('resumatorJWT');

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

// TODO: Rename userId to email, because it is an email.
const router = (
  <Provider store={store}>
    <Router history={history}>
      <Route path="/" component={ PublicContainer } onEnter={handleEnter}>
        <IndexRoute component={ PublicHome } onEnter={handleEnter} />

        <Route path="new" component={ PublicEmployeesCreate } onEnter={handleEnter} />

        <Route path="employees" requireSession>
          <IndexRoute component={ PublicEmployeesList } onEnter={handleEnter} />

          <Route path="new" component={ PublicEmployeesCreate } onEnter={handleEnter} />

          <Route path=":userId">
            <IndexRoute component={ PublicEmployeesShow } onEnter={handleEnter} />

            <Route path="edit" component={ PublicEmployeesEdit }>
              <Route path=":section" onEnter={handleEnter} />
            </Route>
          </Route>
        </Route>

        <Route path="freelancers" requireSession>
          <IndexRoute component={ PublicFreelancersList } onEnter={handleEnter} />

          <Route path="new" component={ PublicFreelancersCreate } onEnter={handleEnter} />

          <Route path=":userId">
            <IndexRoute component={ PublicFreelancersShow } onEnter={handleEnter} />

            <Route path="edit" component={ PublicFreelancersEdit } onEnter={handleEnter} />
          </Route>
        </Route>

        <Route path="prospects" requireSession>
          <IndexRoute component={ PublicProspectsList } onEnter={handleEnter} />

          <Route path="new" component={ PublicProspectsCreate } onEnter={handleEnter} />

          <Route path=":userId">
            <IndexRoute component={ PublicProspectsShow } onEnter={handleEnter} />

            <Route path="edit" component={ PublicProspectsEdit } onEnter={handleEnter} />
          </Route>
        </Route>

        <Route path=":userId">
          <IndexRoute component={ PublicEmployeesShow } onEnter={handleEnter} />

          <Route path="edit" onEnter={handleEnter}>
            <Route path=":pageNumber" component={ PublicEmployeesEdit } onEnter={handleEnter} />
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
window.gapi.load('auth2', () => {
  window.googleAuth = window.gapi.auth2.init({
    client_id: '51676129755-uau4n1hquet98qspphptkkjb4rlnsqjh.apps.googleusercontent.com',
    hosted_domain: 'sytac.io',
  });

  store.dispatch(initialize());

  // When the DOM is ready, render to it.
  domready(() => {
    ReactDOM.render(router, rootElement);
  });
});
