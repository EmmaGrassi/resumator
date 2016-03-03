import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

class LanguagesForm extends React.Component {
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

  renderProficiency() {
    return (
      <Input
        ref="proficiency"
        type="text"
        placeholder="Proficiency"
        label="Proficiency"
      />
    );
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

