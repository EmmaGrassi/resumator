import React from 'react';
import { RouteHandler, State } from 'react-router';
import { Grid, Col, Row, Panel } from 'react-bootstrap';

class NewView extends React.Component {
  render() {
    const panelHeader = (
      <Row>
        <Col xs={12}>
          <strong>Users</strong>
        </Col>
      </Row>
    );

    return (
      <Grid className="NewView">
        <Row>
          <Col xs={12}>
            <Panel header={panelHeader}>
              New
            </Panel>
          </Col>
        </Row>
      </Grid>
    );
  }
}

NewView.displayName = 'NewView';
NewView.mixins = [ State ];

export default NewView;
