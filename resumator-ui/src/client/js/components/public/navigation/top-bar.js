const React = require('react');
const { connect } = require('react-redux');

const Nav = require('react-bootstrap/lib/Nav');
const Navbar = require('react-bootstrap/lib/Navbar');
const NavbarBrand = require('react-bootstrap/lib/NavbarBrand');
const NavbarCollapse = require('react-bootstrap/lib/NavbarCollapse');
const NavbarHeader = require('react-bootstrap/lib/NavbarHeader');
const NavbarToggle = require('react-bootstrap/lib/NavbarToggle');
const NavItem = require('react-bootstrap/lib/NavItem');

const RightButton = require('./right-button');

class TopBar extends React.Component {
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

TopBar.displayName = 'TopBar';

function select(state) {
  return {
  };
}

module.exports = connect(select)(TopBar);
