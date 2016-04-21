import React from 'react';
import { connect } from 'react-redux';

import Nav from 'react-bootstrap/lib/Nav';
import Navbar from 'react-bootstrap/lib/Navbar';
import NavbarBrand from 'react-bootstrap/lib/NavbarBrand';
import NavbarCollapse from 'react-bootstrap/lib/NavbarCollapse';
import NavbarHeader from 'react-bootstrap/lib/NavbarHeader';
import NavbarToggle from 'react-bootstrap/lib/NavbarToggle';
import NavItem from 'react-bootstrap/lib/NavItem';

import RightNav from '../RightNav';

function mapStateToProps(state) {
  return {
    profile: state.user.profile.toJS(),
    session: state.user.session.toJS(),
  };
}

function mapDispatchToProps(dispatch) {
  return {
  };
}

export class TopBar extends React.Component {

  render() {
    let nav = '';
    const homeURL = (this.props.session.idToken &&
        !this.props.profile.item.admin &&
        this.props.profile.item.type) ?
        `#/${this.props.profile.item.type.toLowerCase()}s/${this.props.session.email}` :
        '#/';

    if (this.props.session.idToken && this.props.profile.item.admin) {
      nav = (
        <Nav>
          <NavItem eventKey={1} href="#/employees">Employees</NavItem>
          <NavItem eventKey={2} href="#/freelancers">Freelancers</NavItem>
          <NavItem eventKey={3} href="#/prospects">Prospects</NavItem>
        </Nav>
      );
    }


    return (
      <Navbar>
        <NavbarHeader>
          <NavbarBrand>
            <a href={homeURL}>Resumator</a>
          </NavbarBrand>
          <NavbarToggle />
        </NavbarHeader>
        <NavbarCollapse>
          {nav}
          <RightNav />
        </NavbarCollapse>
      </Navbar>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(TopBar);
