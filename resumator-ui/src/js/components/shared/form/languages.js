import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

import FormComponent from './form';

import proficiencies from '../../../data/proficiencies';

class LanguagesForm extends FormComponent {
  constructor(props) {
    super(props);
    this.props = props;
  }

  handleChange(name) {
    this.props.values[name] = this.refs[name].getValue();

    this.props.handleChange('languages', this.props.currentValues);
  }

  renderName() {
    return this.getInput('name', null, true, null, true);
  }

  renderProficiency() {
    return this.getDropDown('proficiency', proficiencies, true);
  }

  render() {
    return (
      <div className="multi-form">
        <Row>
          <Col xs={6}>
            {this.renderName()}
          </Col>

          <Col xs={6}>
            {this.renderProficiency()}
          </Col>
        </Row>
      </div>
    );
  }
}

export default LanguagesForm;
