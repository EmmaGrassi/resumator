import React from 'react';
import { connect } from 'react-redux';

import NavItem from 'react-bootstrap/lib/NavItem';

// TODO: Add checking logged in state through flux.
class RightButton extends React.Component {
  render() {
    return <NavItem eventKey={2} href="#" id="login-button">Log In</NavItem>;
  }

  componentDidMount() {
    gapi.signin2.render('login-button', {
      scope: 'https://www.googleapis.com/auth/plus.login',
      width: 150,
      height: 50,
      longtitle: false,
      theme: 'light',
      onsuccess: () => {
        console.log('success!');
        debugger;
      },
      onfailure: (error) => {
        console.log(error);
        debugger;
      }
    });
  }
}

RightButton.displayName = 'RightButton';

RightButton.mapStateToProps = function mapStateToProps(state) {
  return {
  };
};

export default connect(RightButton.mapStateToProps)(RightButton);
