import React from 'react';
import { RouteHandler, State } from 'react-router';
import { Grid, Col, Row } from 'react-bootstrap';

import log from 'loglevel';

import RoutedPageComponent from '../../../../lib/react/components/page/RoutedPageComponent';
import NewViewComponent from './NewViewComponent';

class NewPage extends RoutedPageComponent {
  showUser() {
    log.debug('NewPage#showUser');

    const params = this.reactRouter.getCurrentParams();

    const id = params.id;

    if (!id) {
      this.navigateToIndex();
      return;
    }

    this.domainActions.show({
      id: id
    });
  }

  componentWillMount() {
    log.debug('NewPage#componentWillMount');

    this.reactRouter = this.context.application.router.reactRouter;
    this.domainFlux = this.context.application.api.domain.User;
    this.domainKey = this.domainFlux.key;
    this.domainActions = this.domainFlux.getActions(this.domainKey);
    this.domainStore = this.domainFlux.getStore(this.domainKey);

    // Reset the store state
    // TODO: Find a better - generalized - place for this.
    this.domainStore.state.current = null;
  }

  componentDidMount() {
    log.debug('NewPage#componentDidMount');

    this._showUser();
  }

  componentWillReceiveProps() {
    log.debug('NewPage#componentWillReceiveProps');

    this._showUser();
  }

  navigateToIndex() {
    log.debug('NewPage#navigateToIndex');

    this.reactRouter.navigateTo(`/cms/${this.domainKey}`);
  }

  render() {
    log.debug('NewPage#render');

    return (
      <Grid className="NewPage">
        <Row>
          <Col xs={12}>
            {this._bindFlux(<NewView/>)}
          </Col>
        </Row>
      </Grid>
    );
  }
}

NewPage.displayName = 'NewPage';
NewPage.mixins = [ State ];

NewPage.contextTypes = {
  application: React.PropTypes.object
};

export default NewPage;
