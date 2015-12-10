import React from 'react';
import { connect } from 'react-redux';

import _ContainerComponent from '../../../../common/lib/react/ContainerComponent';

import CMSNavigationComponent from './CMSNavigationComponent';

export default class CMSContainerComponent extends _ContainerComponent {
  redirectUnlessLoggedIn(props = this.props) {
    if (!props.token) {
      this.props.history.pushState(null, '/login');
    }
  }

  componentWillMount() {
    this.redirectUnlessLoggedIn();
  }

  componentWillUpdate() {
    this.redirectUnlessLoggedIn();
  }

  componentWillReceiveProps(props) {
    this.redirectUnlessLoggedIn(props);
  }
  render() {
    return (
      <div className="CMSContainerComponent">
        <CMSNavigationComponent
          history={this.props.history}
          location={this.props.location}
          params={this.props.params}
          route={this.props.route}
          routeParams={this.props.routeParams}
          routes={this.props.routes}
          />
        {this.props.children}
      </div>
    );
  }
}

CMSContainerComponent.displayName = 'CMSContainerComponent';

CMSContainerComponent.mapStateToProps = function mapStateToProps(state) {
  return {
    token: state.authentication.token
  };
};

export default connect(CMSContainerComponent.mapStateToProps)(CMSContainerComponent);
