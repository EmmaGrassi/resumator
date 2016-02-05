import React from 'react';
import qwest from 'qwest';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';

import MenuItem from 'react-bootstrap/lib/MenuItem';
import Nav from 'react-bootstrap/lib/Nav';
import NavDropdown from 'react-bootstrap/lib/NavDropdown';
import NavItem from 'react-bootstrap/lib/NavItem';

import logout from '../../../actions/user/logout';
import login from '../../../actions/user/login';

function mapStateToProps(state) {
  return {
    session: state.user.session
  };
}

function mapDispatchToProps(dispatch) {
  return {
    login: (data) => dispatch(login(data)),
    logout: () => dispatch(logout()),
  };
}

class RightNav extends React.Component {
  handleLogInButtonClick(event) {
    event.preventDefault();

    // TODO: Error handling. Apparently the catch on this is not valid.
    window.googleAuth.signIn()
      .then(this.handleLogInSuccess.bind(this));
  }

  handleLogInSuccess(user) {
    const authResponse = user.getAuthResponse();
    const basicProfile = user.getBasicProfile();

    const idToken = authResponse.id_token;
    const expiresAt = authResponse.expires_at;

    const imageUrl = basicProfile.getImageUrl();
    const email = basicProfile.getEmail();
    const [ name, surname ] = basicProfile.getName().split(' ');

    this.props.login({
      idToken,
      expiresAt,

      email,
      imageUrl,
      name,
      surname
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

  render() {
    const { session } = this.props;

    if (session.idToken) {
      const title = `${session.name} ${session.surname}`;

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
          <NavItem
            eventKey={2}
            onClick={this.handleLogInButtonClick.bind(this)}
          >
            Log In
          </NavItem>
        </Nav>
      );
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(RightNav);
