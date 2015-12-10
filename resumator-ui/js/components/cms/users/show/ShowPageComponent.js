import _ from 'lodash';
import React from 'react';
import { Grid, Col, Row } from 'react-bootstrap';
import { connect } from 'react-redux';

import log from 'loglevel';

import RoutedPageComponent from '../../../../lib/react/components/page/RoutedPageComponent';
import ShowViewComponent from './ShowViewComponent';

import { cmsShow } from '../../../../actions';

class ShowPageComponent extends RoutedPageComponent {
  constructor(options = {}) {
    log.debug('ShowPageComponent#constructor');

    super(options);

    _.bindAll(this, [
      'transitionToPublicHomePage'
    ]);
  }

  showUser() {
    log.debug('ShowPageComponent#showUser');

    const id = this.props.params.id;

    if (!id) {
      console.error(`Could not find item with id: "${id}".`);

      this.transitionToIndex();
      return;
    }

    this.props.dispatch(cmsShow(id));
  }

  componentWillMount() {
    log.debug('ShowPageComponent#componentWillMount');

    this.showUser();
  }

  transitionToIndex() {
    log.debug('ShowPageComponent#transitionToIndex');

    this.transitionTo(`/cms/users`);
  }

  transitionToPublicHomePage(id) {
    log.debug('ShowPageComponent#transitionToPublicHomePage');

    const history = this.props.history;

    return () => {
      this.transitionTo(`/`);
    };
  }

  render() {
    log.debug('ShowPageComponent#render');

    const { id, current } = this.props;

    return (
      <Grid className="ShowPage">
        <Row>
          <Col xs={12}>
            <ShowViewComponent
              id={id}
              current={current}
            />
          </Col>
        </Row>
      </Grid>
    );
  }
}

ShowPageComponent.displayName = 'ShowPageComponent';

ShowPageComponent.mapStateToProps = function mapStateToProps(state) {
  return {
    current: state.cms.models.current
  };
};

export default connect(ShowPageComponent.mapStateToProps)(ShowPageComponent);
