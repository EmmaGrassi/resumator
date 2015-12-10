import _ from 'lodash';
import React from 'react';
import { Grid, Col, Row } from 'react-bootstrap';
import { connect } from 'react-redux';

import log from 'loglevel';

import RoutedPageComponent from '../../../../lib/react/components/page/RoutedPageComponent';
import EditViewComponent from './CMSUsersEditViewComponent';

import actions from '../../../../actions';

class CMSUsersEditPageComponent extends RoutedPageComponent {
  constructor(options = {}) {
    log.debug('CMSUsersEditPageComponent#constructor');

    super(options);

    _.bindAll(this, [
      'transitionToIndex',
      'onFormSubmit'
    ]);
  }

  showUser() {
    log.debug('CMSUsersEditPageComponent#showUser');

    const id = this.props.params.id;

    if (!id) {
      console.error(`Could not find item with id: "${id}".`);

      this.transitionToIndex();
      return;
    }

    this.props.dispatch(actions.cmsShow(id));
  }

  componentWillMount() {
    log.debug('CMSUsersEditPageComponent#componentWillMount');

    this.showUser();
  }

  transitionToIndex() {
    log.debug('CMSUsersEditPageComponent#transitionToIndex');

    this.transitionTo(`/cms/users`);
  }

  isLoading() {
    log.debug('CMSUsersEditPageComponent#isLoading');

    return !!this.props.current.item.id;
  }

  onFormSubmit(form) {
    log.debug('CMSUsersEditPageComponent#onFormSubmit');

    const isValid = form.validate();
    const data = form.cleanedData;

    if (isValid) {
      this.props.dispatch(actions.cmsUpdate(data));
    }
  }

  redirectUnlessLoggedIn(props = this.props) {
    if (!props.token) {
      this.transitionTo(`/contact`);
    }
  }

  componentWillReceiveProps(props) {
    this.redirectUnlessLoggedIn(props);
  }

  render() {
    log.debug('CMSUsersEditPageComponent#render');

    let content;

    if (this.isLoading()) {
      content = <EditViewComponent current={this.props.current} onSubmit={this.onFormSubmit}/>;
    }

    return (
      <Grid className="EditPage">
        <Row>
          <Col xs={12}>
            {content}
          </Col>
        </Row>
      </Grid>
    );
  }
}

CMSUsersEditPageComponent.displayName = 'CMSUsersEditPageComponent';

CMSUsersEditPageComponent.mapStateToProps = function mapStateToProps(state) {
  log.debug(`EditPageComponent.mapStateToProps`, state);

  return {
    current: state.cms.models.current
  };
};

export default connect(CMSUsersEditPageComponent.mapStateToProps)(CMSUsersEditPageComponent);
