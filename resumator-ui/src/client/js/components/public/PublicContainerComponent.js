import React from 'react';

import PublicNavigationComponent from './PublicNavigationComponent';

export default class PublicContainerComponent extends React.Component {
  render() {
    return (
      <div className="PublicContainerComponent">
        <PublicNavigationComponent
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

PublicContainerComponent.displayName = 'PublicContainerComponent';
