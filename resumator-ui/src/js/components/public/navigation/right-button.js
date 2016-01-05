const React = require('react');
const qwest = require('qwest');
const { bindAll } = require('lodash');
const { connect } = require('react-redux');

const NavItem = require('react-bootstrap/lib/NavItem');

const actions = require('../../../actions');

class RightButton extends React.Component {
  displayName = 'RightButton';

  constructor() {
    super();

    bindAll(this, [
      'handleSignInError',
      'handleSignInSuccess'
    ]);
  }

  handleSignInSuccess(user) {
    const token = user.getAuthResponse().id_token;
    const basicProfile = user.getBasicProfile();

    const id = basicProfile.getId();
    const imageUrl = basicProfile.getImageUrl();
    const email = basicProfile.getEmail();
    const [ name, surname ] = basicProfile.getName().split(' ');

    this.props.dispatch(actions.user.login.success({
      email,
      id,
      imageUrl,
      name,
      surname,
      token
    }));
  }

  // TODO: Implement
  handleSignInError() {
    console.error(arguments);
  }

  componentDidMount() {
    const loginButtonElement = document.getElementById('login-button');
    let auth2;

    gapi.load('auth2', () => {
      auth2 = gapi.auth2.init({
        client_id: '49560145160-80v99olfohmo0etbo6hugpo337p5d1nl.apps.googleusercontent.com',
        cookiepolicy: 'single_host_origin'
      });

      auth2.attachClickHandler(loginButtonElement, {}, this.handleSignInSuccess, this.handleSignInError);
    });
  }

  render() {
    return <NavItem eventKey={2} href="#" id="login-button">Log In</NavItem>;
  }
}

RightButton.mapStateToProps = function mapStateToProps(state) {
  return {
    user: state.user
  };
};

module.exports = connect(RightButton.mapStateToProps)(RightButton);
