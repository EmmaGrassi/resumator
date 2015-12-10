import _ from 'lodash';
import React from 'react';
import { Col, Grid, Panel, Row, Input, Button } from 'react-bootstrap';
import { connect } from 'react-redux';

import actions from '../../actions';

import Card from 'material-ui/lib/card/card';
import CardText from 'material-ui/lib/card/card-text';
import CardTitle from 'material-ui/lib/card/card-title';
import FontIcon from 'material-ui/lib/font-icon';
import RaisedButton from 'material-ui/lib/raised-button';
import TextField from 'material-ui/lib/text-field';

import RoutedPageComponent from '../../lib/react/components/page/RoutedPageComponent';

class PublicLoginPageComponent extends RoutedPageComponent {
  constructor(props, context) {
    super(props, context);

    this.application = this.context.application;

    _.bindAll(this, [
      '_handleButtonOnClick'
    ]);
  }

  _handleButtonOnClick() {
    const email = document.querySelector('#email').value;
    const password = document.querySelector('#password').value;

    this.props.dispatch(actions.login(email, password));
  }

  redirectIfLoggedIn(props = this.props) {
    if (props.token) {
      this.props.history.pushState(null, '/cms');
    }
  }

  componentWillMount() {
    this.redirectIfLoggedIn();
  }

  componentWillUpdate() {
    this.redirectIfLoggedIn();
  }

  componentWillReceiveProps(props) {
    this.redirectIfLoggedIn(props);
  }

  render() {
    const inputProps = {};
    const buttonProps = {
      onClick: this._handleClickButton
    };

    if (this.props && this.props.code === 'LOGIN_FAILED') {
      inputProps.bsStyle = 'error';
      buttonProps.bsStyle = 'error';
    } else {
      buttonProps.bsStyle = 'primary';
    }

    return <Card>
      <CardTitle
        title="Login"
        subtitle="Content Management System"
        />
      <CardText>
        <TextField
          id="email"
          // hintText="Hint Text"
          floatingLabelText="Username / Email"
        /><br/>
        <TextField
          id="password"
          // hintText="Hint Text"
          floatingLabelText="Password"
          type="password"
        /><br/>
        <RaisedButton
          primary={true}
          onClick={this._handleButtonOnClick}
        >
          <FontIcon className="muidocs-icon-action-lock-open" />
          LOGIN
        </RaisedButton>
      </CardText>
    </Card>;
  }
}

PublicLoginPageComponent.displayName = 'PublicLoginPageComponent';

PublicLoginPageComponent.mapStateToProps = function mapStateToProps(state) {
  return {
    token: state.authentication.token
  };
};

export default connect(PublicLoginPageComponent.mapStateToProps)(PublicLoginPageComponent);
