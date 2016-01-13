import 'babel-polyfill';

import React from 'react';
import ReactDOM from 'react-dom';
import createHistory from 'history/lib/createHashHistory';
import domready from 'domready';
import { IndexRoute, Route, Router } from 'react-router';
import { Provider } from 'react-redux';

// Redux
import actions from './actions';
import reducers from './reducers';
import _store from './store';
const store = _store(reducers, {});

// Components
import AdminContainer from './components/admin/container';
import AdminEmployeesEdit from './components/admin/employees/edit';
import AdminEmployeesList from './components/admin/employees/list';
import AdminEmployeesShow from './components/admin/employees/show';
import AdminHome from './components/admin/home';
import PublicContainer from './components/public/container';
import PublicHome from './components/public/home';
import PublicLogin from './components/public/login';
import PublicLogout from './components/public/logout';
import PublicProfileEdit from './components/public/profile/edit';
import PublicProfileNew from './components/public/profile/new';
import PublicProfilePreview from './components/public/profile/preview';
import PublicProfileShow from './components/public/profile/show';

const rootElement = document.getElementById('root');

const history = createHistory({
  queryKey: false
});

const router = (
  <Provider store={store} >
    <Router history={history} >
      <Route path="admin" component={ AdminContainer } >
        <IndexRoute component={ AdminHome } />

        <Route path="employees" component={ AdminEmployeesList } >
          <Route path=":userId" component={ AdminEmployeesShow } >
            <Route path="edit" component={ AdminEmployeesEdit } />
          </Route>
        </Route>
      </Route>

      <Route path="/" component={ PublicContainer }>
        <IndexRoute component={ PublicHome } />

        <Route path="new" component={ PublicProfileNew } />

        <Route path="preview" component={ PublicProfilePreview } />

        <Route path="login" component={ PublicLogin } />
        <Route path="logout" component={ PublicLogout } />

        <Route path=":userId">
          <IndexRoute component={ PublicProfileShow } />

          <Route path="edit" component={ PublicProfileEdit } />
        </Route>
      </Route>
    </Router>
  </Provider>
);

domready(() => {
  ReactDOM.render(router, rootElement);
});
