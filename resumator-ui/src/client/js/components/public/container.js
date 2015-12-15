import React from 'react';

// import PublicNavigationComponent from './PublicNavigationComponent';
import TopBar from './navigation/top-bar';

export default class Container extends React.Component {
  render() {
    return (
      <div>
        <TopBar
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

Container.displayName = 'Container';
