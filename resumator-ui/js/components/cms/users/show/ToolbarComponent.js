import React from 'react';
import {
  Button,
  ButtonGroup,
  Col,
  Glyphicon,
  Input,
  Row
} from 'react-bootstrap';

import log from 'loglevel';

class ToolbarComponent extends React.Component {
  render() {
    log.debug('ToolbarComponent#componentWillMount');

    const { current } = this.props;

    const editLink = `#/cms/users/${current.id}/edit`;
    const destroyLink = `#/cms/users/${current.id}/destroy`;

    return (
      <Row className="Toolbar">
        <Col xs={12}>
          <div className="pull-left">
            <ButtonGroup
              className="pull-right"
            >
              <Button href={editLink} bsStyle="primary"><Glyphicon glyph="pencil"/>&nbsp; Edit</Button>
              <Button href={destroyLink} bsStyle="danger"><Glyphicon glyph="trash"/>&nbsp; Destroy</Button>
            </ButtonGroup>
          </div>
        </Col>
      </Row>
    );
  }
}

ToolbarComponent.displayName = 'ToolbarComponent';

export default ToolbarComponent;
