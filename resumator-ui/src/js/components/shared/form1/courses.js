import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

class CoursesForm extends React.Component {
  renderName() {
    return (
      <Input
        ref="name"
        type="text"
        placeholder="Name"
        label="Name"
      />
    );
  }

  renderYear() {
    return (
      <Input
        ref="year"
        type="number"
        placeholder="Year"
        label="Year"
      />
    );
  }

  renderDescription() {
    return (
      <Input
        ref="description"
        type="textarea"
        placeholder="Description"
        label="Description"
      />
    );
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

