import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

import FormComponent from './form';

class LanguagesForm extends FormComponent {

  constructor(props) {
    super(props);
    this.props = props;
  }

  handleChange(name, event) {
    this.props.values[name] = this.refs[name].getValue();

    this.props.handleChange('languages', this.props.currentValues);
  }

  renderName() {
    return this.getInput('name');
  }

  renderProficiency() {
    return this.getInput('proficiency');
  }

  render() {
    return (
      <div>
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
