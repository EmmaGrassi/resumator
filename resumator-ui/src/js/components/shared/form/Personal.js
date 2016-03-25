import React from 'react';
import { Button, Col, Input, Row, Alert } from 'react-bootstrap';

import nationalities from '../../../data/nationalities';
import countries from '../../../data/countries';
import types from '../../../data/types';
import labelize from '../../../helpers/labelize';
import FormComponent from './Form';

class PersonalForm extends FormComponent {
  handleChange(name, event) {
    this.props.handleChange(name, this.refs[name].getValue());
  }

  handleQuillChange(name, value) {
    this.props.handleChange(name, value);
  }

  renderFirstRow() {
    if (this.props.profile.item.admin) {
      return (<Row>
        <Col xs={6}>
          {this.renderType()}
        </Col>
        <Col xs={6}>
          {this.renderRole()}
        </Col>
      </Row>);
    }

    return (<Row>
      <Col xs={12}>
        {this.renderRole()}
      </Col>
    </Row>);
  }

  renderType() {
    return this.getDropDown('type', types, true);
  }

  renderRole() {
    return this.getInput('role', null, true);
  }

  renderName() {
    return this.getInput('name', null, true);
  }

  renderSurname() {
    return this.getInput('surname', null, true);
  }

  renderEmail() {
    return this.getInput('email', null, true);
  }

  renderPhonenumber() {
    return this.getInput('phonenumber', 'text', true);
  }

  renderCurrentResidence() {
    return (<Row>
        <Col xs={6}>
          {this.getInput('cityOfResidence', null, true)}
        </Col>
        <Col xs={6}>
          {this.getDropDown('countryOfResidence', countries, true)}
        </Col>
      </Row>
    );
  }

  renderGithub() {
    return this.getInput('github', null, false);
  }

  renderLinkedin() {
    return this.getInput('linkedin', null, false);
  }

  renderDateOfBirth() {
    return this.getInput('dateOfBirth', 'date', true);
  }

  renderNationality() {
    return this.getDropDown('nationality', nationalities, true);
  }

  renderAboutMe() {
    return this.getQuillEditor('aboutMe', true);
  }

  renderCancelButton() {
    if (this.props.register) {
      return;
    }

    return (
      <Button
        bsStyle="danger"
        onClick={this.props.handleCancel}
      >
        Cancel
      </Button>
    );
  }

  renderSaveButton() {
    return (
      <Button
        bsStyle="primary"
        onClick={this.props.handleSubmit}
      >
        Save
      </Button>
    );
  }

  render() {
    return (
      <form onSubmit={this.props.handleSubmit}>
        {this.renderFirstRow()}
        <Row>
          <Col xs={6}>
            {this.renderName()}
          </Col>

          <Col xs={6}>
            {this.renderSurname()}
          </Col>
        </Row>

        <Row>
          <Col xs={6}>
            {this.renderEmail()}
          </Col>

          <Col xs={6}>
            {this.renderPhonenumber()}
          </Col>
        </Row>

        {this.renderCurrentResidence()}

        <Row>
          <Col xs={6}>
            {this.renderGithub()}
          </Col>

          <Col xs={6}>
            {this.renderLinkedin()}
          </Col>
        </Row>

        <Row>
          <Col xs={6}>
            {this.renderDateOfBirth()}
          </Col>

          <Col xs={6}>
            {this.renderNationality()}
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            {this.renderAboutMe()}
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            <div className="pull-right">
              {this.renderCancelButton()}
              {this.renderSaveButton()}
            </div>
          </Col>
        </Row>
      </form>
    );
  }
}

export default PersonalForm;
