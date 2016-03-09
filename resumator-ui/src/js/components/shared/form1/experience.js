import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

import labelize from '../../../helpers/labelize';

class ExperienceForm extends React.Component {

  getInput(name, type) {
    const { isSaving, hasFailed, errors, values } = this.props;
    const inputName = name;
    const inputLabel = labelize(name);

    const props = {
      ref: inputName,
      type: type || 'text',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,

      // TODO: Implement this.
      value: values[inputName],

      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (<Input {...props} />);
  }

  handleChange(name, event) {
    this.props.values[name] = this.refs[name].getValue();

    this.props.handleChange('experience', this.props.currentValues);
  }

  renderTitle() {
    return this.getInput('title');
  }

  renderCompanyName() {
    return this.getInput('companyName');
  }

  renderCity() {
    return this.getInput('city');
  }

  renderCountry() {
    return this.getInput('country');
  }

  renderStartDate() {
    return this.getInput('startDate', 'date');
  }

  renderEndDate() {
    return this.getInput('endDate', 'date');
  }

  renderShortDescription() {
    return this.getInput('shortDescription', 'textarea');
  }

  renderTechnologies() {
    return this.getInput('technologies');
  }

  renderMethodologies() {
    return this.getInput('methodologies');
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
