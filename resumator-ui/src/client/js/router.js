const React = require('react');
const ReactDOM = require('react-dom');
const _ = require('lodash');
const reactRouter = require('react-router');
const ReactRouter = reactRouter.Router;
const {
  IndexRoute,
  Route
} = reactRouter;
const createHistory = require('history/lib/createHashHistory');
const { Provider } = require('react-redux');

const PublicContainer = require('./components/public/container');
const PublicHome = require('./components/public/home');
const PublicLogin = require('./components/public/login');
const PublicLogout = require('./components/public/logout');
const PublicProfileEdit = require('./components/public/profile/edit');
const PublicProfileNew = require('./components/public/profile/new');
const PublicProfilePreview = require('./components/public/profile/preview');
const PublicProfileShow = require('./components/public/profile/show');

const AdminContainer = require('./components/admin/container')
const AdminHome = require('./components/admin/home');
const AdminEmployeesList = require('./components/admin/employees/list');
const AdminEmployeesShow = require('./components/admin/employees/show');
const AdminEmployeesEdit = require('./components/admin/employees/edit');

class Router {
  constructor(options) {
    this.store = options.store;
    this.rootElement = options.rootElement || document.body;

    this.router = this.getRouter();
  }

  // - /
  //   - If the user is not logged in, redirect to /login.
  //   - If the user is logged in and is a normal user, redirect to /:userId.
  //   - If the user is logged in and is an admin user, redirect to /admin.

  // - /login
  //   - If the user is not logged in, show the login page.
  //   - If the user is logged in and is a normal user, redirect to /:userId.
  //   - If the user is logged in and is an admin user, redirect to /admin.

  // - /logout
  //   - Log out and redirect to /login.

  // - /:userId
  //   - If the user is not logged in, redirect to /login.
  //   - If the user is logged in and is an admin user, redirect to /admin/:userId.
  //   - If the user is logged in and is a normal user, show the page.

  // - /:userId/edit
  //   - If the user is not logged in, redirect to /login.
  //   - If the user is logged in and is an admin user, redirect to /admin/:userId/edit.
  //   - If the user is logged in and is a normal user, show the page.

  // - /admin
  //   - If the user is not logged in, redirect to /login.
  //   - If the user is logged in and is a normal user, redirect to /:userId.
  //   - If the user is logged in and is an admin user, show the page.

  // - /admin/employees
  //   - If the user is not logged in, redirect to /login.
  //   - If the user is logged in and is a normal user, redirect to /:userId.
  //   - If the user is logged in and is an admin user, show the page.

  // - /admin/employees/:userId
  //   - If the user is not logged in, redirect to /login.
  //   - If the user is logged in and is a normal user, redirect to /:userId.
  //   - If the user is logged in and is an admin user, show the page.

  // - /admin/employees/:userId/edit
  //   - If the user is not logged in, redirect to /login.
  //   - If the user is logged in and is a normal user, redirect to /:userId.
  //   - If the user is logged in and is an admin user, show the page.
  getRouter() {
    const history = createHistory({
      queryKey: false
    });

    return (
      <Provider store={this.store}>
        <ReactRouter
          history={history}
        >
          <Route path="/">
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
          </Route>
        </ReactRouter>
      </Provider>
    );
  }

  start() {
    ReactDOM.render(this.router, this.rootElement);
  }
}

module.exports = Router;
