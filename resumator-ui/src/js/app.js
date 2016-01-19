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
import PublicEmployeesPreview from './components/public/employees/preview';
import PublicEmployeesShow from './components/public/employees/show';

const rootElement = document.getElementById('root');

const history = createHistory({
  queryKey: false
});

syncReduxAndRouter(history, store);

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

        <Route path="employees">
          <IndexRoute component={ PublicEmployeesList } />

          <Route path="new" component={ PublicEmployeesCreate } />

          <Route path=":userId">
            <IndexRoute component={ PublicEmployeesShow } />

            <Route path="edit" component={ PublicEmployeesEdit } />
            <Route path="preview" component={ PublicEmployeesPreview } />
          </Route>
        </Route>
      </Route>
    </Router>
  </Provider>
);

store.dispatch(actions.user.getCookie());

domready(() => {
  ReactDOM.render(router, rootElement);
});
