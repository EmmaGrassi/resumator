import _ from 'lodash';
import React from 'react';
import { Grid, Col, Row, Panel } from 'react-bootstrap';
import { connect } from 'react-redux';

import Card from 'material-ui/lib/card/card';
import CardText from 'material-ui/lib/card/card-text';
import CardTitle from 'material-ui/lib/card/card-title';

import log from 'loglevel';

import actions from '../../../../actions';

import RoutedPageComponent from '../../../../lib/react/components/page/RoutedPageComponent';
// import PaginationComponent from './PaginationComponent';
// import ToolbarComponent from './ToolbarComponent';
import CMSUsersListTableComponent from './CMSUsersListTableComponent';

class CMSUsersListPageComponent extends RoutedPageComponent {
  constructor() {
    // log.debug('CMSUsersListPageComponent#constructor');

    super();

    // this.firstPage = 1;
    // this.page = this.firstPage;
    // this.limit = 10;

    this.domainKey = 'CMSUsers';

    _.bindAll(this, [
      'transitionToShowPage'
    ]);
  }

  // TODO: Query / filter.
  listUsers(page = 1, limit = 10) {
    // log.debug('CMSUsersListPageComponent#listUsers');

    this.props.dispatch(actions.cmsList(this.props.authentication, this.domainKey, page, limit));
  }

  componentWillMount() {
    // log.debug('CMSUsersListPageComponent#componentWillMount');

    this.listUsers();
  }

  transitionToShowPage(id) {
    return () => {
      // log.debug('CMSUsersListPageComponent#transitionToShowPage', id);

      this.transitionTo(`/cms/users/${id}`);
    };
  }

  transitionToEditPage(id) {
    return () => {
      // log.debug('CMSUsersListPageComponent#transitionToEditPage', id);

      this.transitionTo(`/cms/users/${id}/edit`);
    };
  }

  transitionToDestroyPage(id) {
    return () => {
      // log.debug('CMSUsersListPageComponent#transitionToDestroyPage', id);

      this.transitionTo(`/cms/users/${id}/destroy`);
    };
  }

  render() {
    // log.debug('CMSUsersListPageComponent#render');

    return <Card>
      <CardTitle
        title={this.domainKey}
        subtitle="List"
        />
      <CardText>
        <CMSUsersListTableComponent
          list={this.props.list}
          transitionToEditPage={this.transitionToEditPage}
          transitionToShowPage={this.transitionToShowPage}
          transitionToDestroyPage={this.transitionToDestroyPage}
        />
      </CardText>
    </Card>;
  }
}

CMSUsersListPageComponent.displayName = 'CMSUsersListPageComponent';

CMSUsersListPageComponent.mapStateToProps = function mapStateToProps(state) {
  return {
    authentication: state.authentication,
    list: state.cms.models.list
  };
};

export default connect(CMSUsersListPageComponent.mapStateToProps)(CMSUsersListPageComponent);
