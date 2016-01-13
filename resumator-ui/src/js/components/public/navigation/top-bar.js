import React from 'react';
import { connect } from 'react-redux';

import Nav from 'react-bootstrap/lib/Nav';
import Navbar from 'react-bootstrap/lib/Navbar';
import NavbarBrand from 'react-bootstrap/lib/NavbarBrand';
import NavbarCollapse from 'react-bootstrap/lib/NavbarCollapse';
import NavbarHeader from 'react-bootstrap/lib/NavbarHeader';
import NavbarToggle from 'react-bootstrap/lib/NavbarToggle';
import NavItem from 'react-bootstrap/lib/NavItem';

import RightButton from './right-button';

class TopBar extends React.Component {
  displayName = 'TopBar';

  render() {
    return <Navbar>
      <NavbarHeader>
        <NavbarBrand>
          <a href="#">Resumator</a>
        </NavbarBrand>
        <NavbarToggle/>
      </NavbarHeader>
      <NavbarCollapse>
        <Nav>
          <NavItem eventKey={1} href="#/123">Profile</NavItem>
        </Nav>
        <Nav pullRight>
          <RightButton/>
        </Nav>
      </NavbarCollapse>
    </Navbar>;
  }
}

function select(state) {
  return {
  };
}

export default connect(select)(TopBar);
