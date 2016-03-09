import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

import FormComponent from './form';

class CoursesForm extends FormComponent {

  constructor(props) {
    super(props);
    this.props = props;
  }

  handleChange(name, event) {
    this.props.values[name] = this.refs[name].getValue();

    this.props.handleChange('courses', this.props.currentValues);
  }

  renderName() {
    return this.getInput('name');
  }

  renderYear() {
    return this.getInput('year', 'number');
  }

  renderDescription() {
    return this.getInput('description', 'textarea');
  }

  render() {
    return (
      <div>
        <Row>
          <Col xs={12}>
            {this.renderName()}
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            {this.renderYear()}
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            {this.renderDescription()}
          </Col>
        </Row>
      </div>
    );
  }
}

export default CoursesForm;
