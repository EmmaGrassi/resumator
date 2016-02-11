import React from 'react';
import { connect } from 'react-redux';

import Nav from 'react-bootstrap/lib/Nav';
import Navbar from 'react-bootstrap/lib/Navbar';
import NavbarBrand from 'react-bootstrap/lib/NavbarBrand';
import NavbarCollapse from 'react-bootstrap/lib/NavbarCollapse';
import NavbarHeader from 'react-bootstrap/lib/NavbarHeader';
import NavbarToggle from 'react-bootstrap/lib/NavbarToggle';
import NavItem from 'react-bootstrap/lib/NavItem';

import RightNav from './right-nav';

function mapStateToProps(state) {
  return {
    session: state.user.session.toJS()
  };
}

function mapDispatchToProps(dispatch) {
  return {
    navigateToHome: () => dispatch(pushPath('/'))
  };
}

class TopBar extends React.Component {
  render() {
    const { session } = this.props;

    let nav = '';

    if (session.idToken) {
      nav = (
        <Nav>
          <NavItem eventKey={1} href="#/employees">Employees</NavItem>
        </Nav>
      );
    }

    return <Navbar>
      <NavbarHeader>
        <NavbarBrand>
          <a href="#">Resumator</a>
        </NavbarBrand>
        <NavbarToggle/>
      </NavbarHeader>
      <NavbarCollapse>
        {nav}
        <RightNav/>
      </NavbarCollapse>
    </Navbar>;
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(TopBar);

