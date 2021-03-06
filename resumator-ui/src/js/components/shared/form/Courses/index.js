import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

import FormComponent from '../Form';

class CoursesForm extends FormComponent {
  constructor(props) {
    super(props);
    this.props = props;
  }

  handleChange(name) {
    this.props.values[name] = this.refs[name].getValue();

    this.props.handleChange('courses', this.props.currentValues);
  }

  renderName() {
    return this.getInput('name', null, true, null, true);
  }

  renderYear() {
    return this.getInput('year', 'number', true);
  }

  renderDescription() {
    return this.getInput('description', 'textarea', true);
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
        {this.renderRemove()}
      </div>
    );
  }
}

export default CoursesForm;
