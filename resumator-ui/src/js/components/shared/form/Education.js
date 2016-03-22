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
    const isOther = this.props.values.degree === 'OTHER';
    return isOther ?
      (<Row>
        <Col xs={6}>
          {this.getDropDown('degree', degrees, true, true)}
        </Col>
        <Col xs={6}>
          {this.getInput('otherDegree', null, true)}
        </Col>
      </Row>) :
      (<Row>
        <Col xs={12}>
          {this.getDropDown('degree', degrees, true, true)}
        </Col>
      </Row>);
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

  renderRemove() {
    if (this.props.showRemoveButton) {
      return (<div className="btn btn-danger"
        onClick={this.props.handleRemove}
      >Remove
      </div>);
    }
  }

  render() {
    return (
      <div className="multi-form">

        {this.renderDegree()}

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
        {this.renderRemove()}
      </div>
    );
  }
}

export default EducationForm;
