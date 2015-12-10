import jQuery from 'jquery';
import React from 'react';
import {
  Button,
  Col,
  Input,
  Row
} from 'react-bootstrap';

import log from 'loglevel';

class ToolbarComponent extends React.Component {
  render() {
    log.debug('ToolbarComponent#componentWillMount');

    return (
      <Row className="Toolbar">
        <Col xs={12}>
          <div className="pull-left">
            <Button href="#/cms/users/new" bsStyle="success">New</Button>
          </div>
          <div className="pull-right">
            <form className="form-inline">
              <Input
                type="text"
                placeholder="Search"
                value={this.props.query}
                buttonAfter={
                  <Button bsStyle="primary">Search</Button>
                }
                onChange={e => {
                  const value = jQuery(e.target).val();

                  this.actions.filterByQuery(value);
                }}
              />
            </form>
          </div>
        </Col>
      </Row>
    );
  }
}

ToolbarComponent.displayName = 'ToolbarComponent';

ToolbarComponent.contextTypes = {
  application: React.PropTypes.object
};

export default ToolbarComponent;
