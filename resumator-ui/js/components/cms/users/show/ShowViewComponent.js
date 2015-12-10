import React from 'react';
import { Grid, Col, Row, Panel, Table } from 'react-bootstrap';

import log from 'loglevel';

import ToolbarComponent from './ToolbarComponent';

class ShowViewComponent extends React.Component {
  render() {
    log.debug('ShowViewComponent#render');

    const { current } = this.props;

    const panelHeader = (
      <Row>
        <Col xs={12}>
          <strong>Users</strong>
        </Col>
      </Row>
    );

    if (current.id) {
      return (
        <Panel header={panelHeader}>
          <Row>
            <Col xs={12}>
              <ToolbarComponent
                current={current}
              />
            </Col>
          </Row>

          <Row>
            <Col xs={12}>
              <Table striped hover bordered>
                <tbody>
                  <tr>
                    <td>id</td>
                    <td>{current.item.id}</td>
                  </tr>
                  <tr>
                    <td>Email</td>
                    <td>{current.item.email}</td>
                  </tr>
                  <tr>
                    <td>Username</td>
                    <td>{current.item.username}</td>
                  </tr>
                </tbody>
              </Table>
            </Col>
          </Row>
        </Panel>
      );
    }

    return (
      <Panel header={panelHeader}>
        <span>Loading...</span>
      </Panel>
    );
  }
}

ShowViewComponent.displayName = 'ShowViewComponent';

export default ShowViewComponent;
