import { Grid, Col, Row } from 'react-bootstrap';

import log from 'loglevel';

import RoutedPageComponent from '../../../../lib/react/components/page/RoutedPageComponent';
import DestroyViewComponent from './DestroyViewComponent';

class DestroyPageComponent extends RoutedPageComponent {
  showUser() {
    log.debug('DestroyPageComponent#showUser');

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
    log.debug('DestroyPageComponent#componentWillMount');

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
    log.debug('DestroyPageComponent#componentDidMount');

    this._showUser();
  }

  componentWillReceiveProps() {
    log.debug('DestroyPageComponent#componentWillReceiveProps');

    this._showUser();
  }

  navigateToIndex() {
    log.debug('DestroyPageComponent#navigateToIndex');

    this.reactRouter.navigateTo(`/cms/${this.domainKey}`);
  }

  render() {
    log.debug('DestroyPageComponent#render');

    return (
      <Grid className="DestroyPage">
        <Row>
          <Col xs={12}>
            {this._bindFlux(<DestroyView/>)}
          </Col>
        </Row>
      </Grid>
    );
  }
}

DestroyPageComponent.displayName = 'DestroyPageComponent';

export default DestroyPageComponent;
