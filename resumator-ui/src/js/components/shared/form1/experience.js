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

  handleChange(name, event) {
    this.props.values[name] = this.refs[name].getValue();

    this.props.handleChange('experience', this.props.currentValues);
  }

  renderTitle() {
    return this.getInput('title', null, true);
  }

  renderCompanyName() {
    return this.getInput('companyName', null, true);
  }

  renderCity() {
    return this.getInput('city', null, true);
  }

  renderCountry() {
    const { isSaving, hasFailed, errors } = this.props;

    const inputName = 'country';
    const inputLabel = labelize(inputName, '*');

    const props = {
      ref: inputName,
      type: 'select',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} >
        {Object.keys(countries).map((key, i) => <option key={i} value={key}>{countries[key]}</option>)}
      </Input>
    );
  }

  renderStartDate() {
    return this.getInput('startDate', 'date', true);
  }

  renderEndDate() {
    return this.getInput('endDate', 'date', true);
  }

  renderShortDescription() {
    return this.getInput('shortDescription', 'textarea', true);
  }

  renderTechnologies() {
    return this.getInput('technologies', null, true);
  }

  renderMethodologies() {
    return this.getInput('methodologies', null, true);
  }

  render() {
    return (
      <div>
        <Row>
          <Col xs={6}>
            {this.renderCompanyName()}
          </Col>

          <Col xs={6}>
            {this.renderTitle()}
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
            {this.renderEndDate()}
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
