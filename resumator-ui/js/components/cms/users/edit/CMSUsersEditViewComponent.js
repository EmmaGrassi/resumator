import BootstrapForm from 'newforms-bootstrap';
import Loader from 'react-loader';
import React from 'react';
import _ from 'lodash';
import flat from 'flat';
import newforms from 'newforms';
import { Grid, Button, Panel } from 'react-bootstrap';

import log from 'loglevel';

import UserFormComponent from '../forms/UserFormComponent';

import { cmsUpdate } from '../../../../actions';

class CMSUsersEditViewComponent extends React.Component {
  constructor(options = {}) {
    log.debug('CMSUsersEditViewComponent#constructor');

    super(options);

    _.bindAll(this, [
      'onSubmit'
    ]);
  }

  onSubmit(event) {
    log.debug('CMSUsersEditViewComponent#onSubmit');

    event.preventDefault();

    const form = this.refs.UserFormComponent.getForm();

    this.props.onSubmit(form);
  }

  render() {
    log.debug('CMSUsersEditViewComponent#render', this.props.current.item);

    const form = (
      <newforms.RenderForm
        ref="UserFormComponent"
        form={UserFormComponent}
        controlled={true}
        data={this.props.current.item}
      >
        <BootstrapForm
          ref="UserFormComponent"
          form={UserFormComponent}
          controlled={true}
          data={this.props.current.item}
        />
      </newforms.RenderForm>
    );

    return (
      <Panel header="Users">
        <form onSubmit={this.onSubmit}>
          {form}

          <Button type="submit" bsStyle="primary">Edit</Button>
        </form>
      </Panel>
    );
  }
}


CMSUsersEditViewComponent.displayName = 'CMSUsersEditViewComponent';

export default CMSUsersEditViewComponent;
