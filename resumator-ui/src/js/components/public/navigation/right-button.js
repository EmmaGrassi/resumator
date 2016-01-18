import React from 'react';
import qwest from 'qwest';
import { connect } from 'react-redux';

import NavItem from 'react-bootstrap/lib/NavItem';

import actions from '../../../actions';

function mapStateToProps(state) {
  return {
    user: state.user
  };
}

function mapDispatchToProps(dispatch) {
  return {
    login: (data) => dispatch(actions.user.login(data)),
    logout: () => dispatch(actions.user.logout())
  };
}

class RightButton extends React.Component {
  constructor() {
    super();

    this.auth2 = null;
  }

  handleLogInSuccess(user) {
    const token = user.getAuthResponse().id_token;
    const basicProfile = user.getBasicProfile();

    const id = basicProfile.getId();
    const imageUrl = basicProfile.getImageUrl();
    const email = basicProfile.getEmail();
    const [ name, surname ] = basicProfile.getName().split(' ');

    this.props.login({
      email,
      id,
      imageUrl,
      name,
      surname,
      token
    });
  }

  // TODO: Implement
  handleLogInError() {
    console.error(arguments);
  }

  // TODO: Does not work right now.
  handleLogOutButtonClick(event) {
    event.preventDefault();
  }

  componentDidMount() {
    const loginButtonElement = document.getElementById('login-button');

    gapi.load('auth2', () => {
      this.auth2 = gapi.auth2.init({
        client_id: '49560145160-80v99olfohmo0etbo6hugpo337p5d1nl.apps.googleusercontent.com',
        cookiepolicy: 'single_host_origin'
      });

      if (loginButtonElement) {
        this.auth2.attachClickHandler(loginButtonElement, {}, this.handleLogInSuccess.bind(this), this.handleLogInError.bind(this));
      }
    });
  }

  render() {
    const user = this.props.user;

    if (user && user.name) {
      return <NavItem eventKey={2} href="#" id="logout-button" onClick={this.handleLogoutButtonClick}>Log Out</NavItem>;
    } else {
      return <NavItem eventKey={2} href="#" id="login-button">Log In</NavItem>;
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(RightButton);
