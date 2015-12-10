import React from 'react';
import { Col, Grid, Panel, Row } from 'react-bootstrap';

import RoutedPageComponent from '../../lib/react/components/page/RoutedPageComponent';

export default class CMSDashboardPageComponent extends RoutedPageComponent {
  render() {
    return (
      <div className="Page">
        <Grid className="DashboardPage">
          <Row>
            <Col xs={6}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>

            <Col xs={6}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>
          </Row>

          <Row>
            <Col xs={3}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>

            <Col xs={3}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>

            <Col xs={3}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>

            <Col xs={3}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>
          </Row>

          <Row>
            <Col xs={6}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>

            <Col xs={6}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>
          </Row>

          <Row>
            <Col xs={4}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>

            <Col xs={8}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>
          </Row>

          <Row>
            <Col xs={6}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>

            <Col xs={6}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>
          </Row>

          <Row>
            <Col xs={8}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>

            <Col xs={4}>
              <Panel header="Header">
                Body
              </Panel>
            </Col>
          </Row>
        </Grid>
      </div>
    );
  }
}

CMSDashboardPageComponent.displayName = 'CMSDashboardPageComponent';
