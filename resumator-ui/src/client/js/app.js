require('babel-polyfill');

const React = require('react');
const ReactDOM = require('react-dom');
const createHistory = require('history/lib/createHashHistory');
const domready = require('domready');
const { IndexRoute, Route, Router } = require('react-router');
const { Provider } = require('react-redux');

// Redux
const actions = require('./actions');
const reducers = require('./reducers');
const store = require('./store')(reducers, {});

// Components
const AdminContainer = require('./components/admin/container');
const AdminEmployeesEdit = require('./components/admin/employees/edit');
const AdminEmployeesList = require('./components/admin/employees/list');
const AdminEmployeesShow = require('./components/admin/employees/show');
const AdminHome = require('./components/admin/home');
const PublicContainer = require('./components/public/container');
const PublicHome = require('./components/public/home');
const PublicLogin = require('./components/public/login');
const PublicLogout = require('./components/public/logout');
const PublicProfileEdit = require('./components/public/profile/edit');
const PublicProfileNew = require('./components/public/profile/new');
const PublicProfilePreview = require('./components/public/profile/preview');
const PublicProfileShow = require('./components/public/profile/show');

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
