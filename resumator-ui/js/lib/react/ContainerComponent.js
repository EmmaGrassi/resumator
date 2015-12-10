import React from 'react';

export default class ContainerComponent extends React.Component {
  render() {
    return (
      <div>{this.props.children}</div>
    );
  }
}

ContainerComponent.displayName = 'ContainerComponent';
