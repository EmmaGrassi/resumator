import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

import FormComponent from './form';
import countries from '../../../data/countries';

class EducationForm extends FormComponent {

  constructor(props) {
    super(props);
    this.props = props;
  }

  handleChange(name, event) {
    this.props.values[name] = this.refs[name].getValue();
    this.props.handleChange('education', this.props.currentValues);
  }

  renderDegree() {
    return this.getInput('degree', null, true);
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
    const { isSaving, hasFailed, errors } = this.props;

    const inputName = 'country';
    const inputLabel = 'Country';

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
