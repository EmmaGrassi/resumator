import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

import FormComponent from './form';
import countries from '../../../data/countries';
import degrees from '../../../data/degrees';

class EducationForm extends FormComponent {

  constructor(props) {
    super(props);
    this.props = props;
  }

  handleChange(name) {
    this.props.values[name] = this.refs[name].getValue();

    this.props.handleChange('education', this.props.currentValues);
  }

  renderDegree() {
    return this.getDropDown('degree', degrees, true, true);
  }

  renderFieldOfStudy() {
    return this.getInput('fieldOfStudy', null, true);
  }

  renderSchool() {
    return this.getInput('school', null, true);
  }

  renderCity() {
    return this.getInput('city', null, true);
  }

  renderCountry() {
    return this.getDropDown('country', countries, true);
  }

  renderStartYear() {
    return this.getInput('startYear', 'number');
  }

  renderEndYear() {
    return this.getInput('endYear', 'number');
  }

  render() {
    return (
      <div>
        <Row>
          <Col xs={12}>
            {this.renderDegree()}
          </Col>
        </Row>

        <Row>
          <Col xs={6}>
            {this.renderSchool()}
          </Col>

          <Col xs={6}>
            {this.renderFieldOfStudy()}
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
            {this.renderStartYear()}
          </Col>

          <Col xs={6}>
            {this.renderEndYear()}
          </Col>
        </Row>
      </div>
    );
  }
}

export default EducationForm;
