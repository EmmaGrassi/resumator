import React from 'react';
import { RouteHandler, State } from 'react-router';
import { Grid, Col, Row, Panel, Table, Button, ButtonGroup, Glyphicon } from 'react-bootstrap';

import log from 'loglevel';

import ToolbarComponent from './ToolbarComponent';

class DestroyViewComponent extends React.Component {
  componentWillMount() {
    log.debug('DestroyViewComponent#componentWillMount');

    this.reactRouter = this.context.application.router.reactRouter;
    this.domainFlux = this.context.application.api.domain.User;
    this.domainKey = this.domainFlux.key;
    this.domainActions = this.domainFlux.getActions(this.domainKey);
    this.domainStore = this.domainFlux.getStore(this.domainKey);
  }

  render() {
    log.debug('DestroyViewComponent#render');

    const current = this.domainStore.state.current;

    const panelHeader = (
      <Row>
        <Col xs={12}>
          <strong>Users</strong>
        </Col>
      </Row>
    );
    let panelContent;

    if (current) {
      panelContent = (
        <Row>
          <Col xs={12}>
            <span>Are you sure you want to destroy this {this.domainKey}?</span>
          </Col>
        </Row>
      );
    } else {
      panelContent = (
        <span>Loading...</span>
      );
    }

    return (
      <Panel header={panelHeader}>
        <Toolbar/>
        {panelContent}
      </Panel>
    );
  }
}

DestroyViewComponent.displayName = 'DestroyViewComponent';
DestroyViewComponent.mixins = [ State ];

DestroyViewComponent.contextTypes = {
  application: React.PropTypes.object
};

export default DestroyViewComponent;
