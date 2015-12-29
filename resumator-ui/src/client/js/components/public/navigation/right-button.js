const React = require('react');
const { bindAll } = require('lodash');
const { connect } = require('react-redux');

const NavItem = require('react-bootstrap/lib/NavItem');

// TODO: Add checking logged in state through flux.
class RightButton extends React.Component {
  constructor() {
    super();

    bindAll(this, [
      'onSignIn'
    ]);
  }

  render() {
    return <NavItem eventKey={2} href="#" id="login-button">Log In</NavItem>;
  }

  onSignIn() {
  }

  componentDidMount() {
    function onSuccess(googleUser) {
      console.log('Logged in as: ' + googleUser.getBasicProfile().getName());
    }
    function onFailure(error) {
      console.log(error);
    }

    gapi.signin2.render('login-button', {
      scope: 'https://www.googleapis.com/auth/plus.login',
      width: 150,
      height: 50,
      longtitle: false,
      theme: 'light',
      onsuccess: this.onSignIn,

      onfailure: () => {
      }
    });
  }
}

RightButton.displayName = 'RightButton';

RightButton.mapStateToProps = function mapStateToProps(state) {
  return {
  };
};

module.exports = connect(RightButton.mapStateToProps)(RightButton);
