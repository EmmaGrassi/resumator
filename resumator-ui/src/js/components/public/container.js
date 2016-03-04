import React from 'react';

import TopBar from './navigation/top-bar';

class Container extends React.Component {

  constructor(props){
    super(props);
    this.props = props;
    this.state = {

    }
  }

  componentDidMount(){
    if( $ && $.material ) $.material.init();
  }

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

export default Container;
