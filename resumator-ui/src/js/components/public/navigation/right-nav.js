import React from 'react';
import qwest from 'qwest';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';

import MenuItem from 'react-bootstrap/lib/MenuItem';
import Nav from 'react-bootstrap/lib/Nav';
import NavDropdown from 'react-bootstrap/lib/NavDropdown';
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
    logout: () => dispatch(actions.user.logout()),
  };
}

class RightNav extends React.Component {
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

  handleLogOutButtonClick(event) {
    event.preventDefault();
    event.stopPropagation();

    this.props.logout();
  }

  componentDidMount() {
    gapi.load('auth2', () => {
      this.auth2 = gapi.auth2.init({
        client_id: '49560145160-80v99olfohmo0etbo6hugpo337p5d1nl.apps.googleusercontent.com',
        cookiepolicy: 'single_host_origin'
      });

      const loginButtonElement = document.getElementById('login-button');

      if (loginButtonElement) {
        this.auth2.attachClickHandler(loginButtonElement, {}, this.handleLogInSuccess.bind(this), this.handleLogInError.bind(this));
      }
    });
  }

  componentDidUpdate() {
    const loginButtonElement = document.getElementById('login-button');

    if (loginButtonElement) {
      this.auth2.attachClickHandler(loginButtonElement, {}, this.handleLogInSuccess.bind(this), this.handleLogInError.bind(this));
    }
  }

  render() {
    const user = this.props.user;
    const token = user.get('token');
    const name = user.get('name');
    const surname = user.get('surname');

    if (token) {
      const title = `${name} ${surname}`;

      return (
        <Nav pullRight>
          <NavDropdown eventKey={2} title={title} id="right-nav-dropdown">
            <MenuItem onClick={this.handleLogOutButtonClick.bind(this)}>Log Out</MenuItem>
          </NavDropdown>
        </Nav>
      );
    } else {
      return (
        <Nav pullRight>
          <NavItem eventKey={2} href="#" key="loginButton" id="login-button">Log In</NavItem>
        </Nav>
      );
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(RightNav);
