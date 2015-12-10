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
  componentWillMount() {
    log.debug('ToolbarComponent#componentWillMount');

    this.reactRouter = this.context.application.router.reactRouter;
    this.domainFlux = this.context.application.api.domain.User;
    this.domainKey = this.domainFlux.key;
    this.domainActions = this.domainFlux.getActions(this.domainKey);
    this.domainStore = this.domainFlux.getStore(this.domainKey);

    this.domainStore.register(this.domainActions.destroy, () => {
      this.transitionToListPage();
    });
  }

  transitionToListPage() {
    this.reactRouter.transitionTo('CMSUsersList', {}, {});
  }

  handleYes() {
    this.domainActions.destroy({
      id: this.domainStore.state.current.id
    });
  }

  handleNo() {
    this.transitionToListPage();
  }

  render() {
    log.debug('ToolbarComponent#componentWillMount');

    const params = this.reactRouter.getCurrentParams();
    const editLink = `#/cms/users/${params.id}/edit`;
    const destroyLink = `#/cms/users/${params.id}/destroy`;

    return (
      <Row className="Toolbar">
        <Col xs={12}>
          <div className="pull-left">
            <ButtonGroup>
              <Button
                className="yes"
                bsStyle="success"
                onClick={() => { this.handleYes(); }}
              >
                <Glyphicon glyph="ok"/> Yes
              </Button>
              <Button
                className="no"
                bsStyle="danger"
                onClick={() => { this.handleNo(); }}
              >
                <Glyphicon glyph="remove"/> No
              </Button>
            </ButtonGroup>
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
