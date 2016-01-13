import React from 'react';
import qwest from 'qwest';
import { bindAll } from 'lodash';
import { connect } from 'react-redux';

import NavItem from 'react-bootstrap/lib/NavItem';

import actions from '../../../actions';

class RightButton extends React.Component {
  displayName = 'RightButton';

  constructor() {
    super();

    this.auth2 = null;

    bindAll(this, [
      'handleLogInError',
      'handleLogInSuccess'
    ]);
  }

  handleLogInSuccess(user) {
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
  handleLogInError() {
    console.error(arguments);
  }

  handleLogOutButtonClick() {
    debugger;
  }

  componentDidMount() {
    const loginButtonElement = document.getElementById('login-button');

    gapi.load('auth2', () => {
      this.auth2 = gapi.auth2.init({
        client_id: '49560145160-80v99olfohmo0etbo6hugpo337p5d1nl.apps.googleusercontent.com',
        cookiepolicy: 'single_host_origin'
      });

      if (loginButtonElement) {
        this.auth2.attachClickHandler(loginButtonElement, {}, this.handleLogInSuccess, this.handleLogInError);
      }
    });
  }

  render() {
    const user = this.props.user;

    if (user && user.name) {
      return <NavItem eventKey={2} href="#" onClick={this.handleLogoutButtonClick}>Log Out</NavItem>;
    } else {
      return <NavItem eventKey={2} href="#" id="login-button">Log In</NavItem>;
    }
  }
}

RightButton.mapStateToProps = function mapStateToProps(state) {
  return {
    user: state.user
  };
};

export default connect(RightButton.mapStateToProps)(RightButton);
