import _ from 'lodash';
import React from 'react';
import log from 'loglevel';
import { connect } from 'react-redux';
import actions from '../../actions';

import RoutedPageComponent from '../../lib/react/components/page/RoutedPageComponent';

class PublicLogoutPageComponent extends RoutedPageComponent {
  redirectToLogin() {
    this.transitionTo('/login');
  }

  shouldRedirect(props = this.props) {
    return !props.token;
  }

  redirectIfLoggedOut(props = this.props) {
    if (this.shouldRedirect(props)) {
      this.redirectToLogin();
    }
  }

  componentWillMount() {
    if (this.shouldRedirect()) {
      this.redirectToLogin();
    } else {
      this.props.dispatch(actions.logout(this.props.token));
    }
  }

  componentWillReceiveProps(props) {
    this.redirectIfLoggedOut(props);
  }

  componentWillUpdate() {
    this.redirectIfLoggedOut();
  }

  render() {
    return (
      <div></div>
    );
  }
}

PublicLogoutPageComponent.displayName = 'PublicLogoutPageComponent';

PublicLogoutPageComponent.mapStateToProps = function mapStateToProps(state) {
  return {
    token: state.authentication.token
  };
};

export default connect(PublicLogoutPageComponent.mapStateToProps)(PublicLogoutPageComponent);
