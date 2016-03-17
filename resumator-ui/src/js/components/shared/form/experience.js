import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

import FormComponent from './form';
import countries from '../../../data/countries';
import labelize from '../../../helpers/labelize';

class ExperienceForm extends FormComponent {

  constructor(props) {
    super(props);
    this.props = props;
  }

  handleChange(name) {
    this.props.values[name] = this.refs[name].getValue();

    this.props.handleChange('experience', this.props.currentValues);
  }

  renderRole() {
    return this.getInput('role', null, true);
  }

  renderCompanyName() {
    return this.getInput('companyName', null, true, null, true);
  }

  renderCity() {
    return this.getInput('city', null, true);
  }

  renderCountry() {
    return this.getDropDown('country', countries, true);
  }

  renderStartDate() {
    return this.getInput('startDate', 'date', true);
  }

  renderEndDate() {
    return this.getInput('endDate', 'date', false);
  }

  renderIWorkHere() {
    const endDate = this.props.values.endDate;
    const checkTheBox = !!endDate && new Date(endDate).getTime() > Date.now();
    return this.getInput(
      'currentlyWorkHere',
      'checkbox',
      false,
      null,
      false,
      { checked: checkTheBox }
    );
  }

  renderShortDescription() {
    return this.getInput('shortDescription', 'textarea', true);
  }

  renderTechnologies() {
    return this.getInput(
      'technologies',
      null,
      true,
      'Separate values with commas, e.g.: Java, JavaScript, ...'
    );
  }

  renderMethodologies() {
    return this.getInput(
      'methodologies',
      null,
      true,
      'Separate values with commas, e.g.: SCRUM, RUP, BEM ...'
    );
  }

  render() {
    return (
      <div className="multi-form">
        <Row>
          <Col xs={6}>
            {this.renderCompanyName()}
          </Col>

          <Col xs={6}>
            {this.renderRole()}
          </Col>
        </Row>

        <Row>
          <Col xs={6}>
            {this.renderCountry()}
          </Col>

          <Col xs={6}>
            {this.renderCity()}
          </Col>
        </Row>

        <Row>
          <Col xs={6}>
            {this.renderStartDate()}
          </Col>

          <Col xs={6}>
            <Row>
              <Col xs={9}>
                {this.renderEndDate()}
              </Col>
              <Col xs={3}>
                {this.renderIWorkHere()}
              </Col>
            </Row>
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            {this.renderTechnologies()}
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            {this.renderMethodologies()}
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            {this.renderShortDescription()}
          </Col>
        </Row>
      </div>
    );
  }
}

export default ExperienceForm;
