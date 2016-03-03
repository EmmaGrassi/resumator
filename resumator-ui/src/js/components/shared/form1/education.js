import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

class EducationForm extends React.Component {
  renderDegree() {
    return (
      <Input
        ref="degree"
        type="text"
        placeholder="Degree"
        label="Degree"
      />
    );
  }

  renderFieldOfStudy() {
    return (
      <Input
        ref="fieldOfStudy"
        type="text"
        placeholder="Field of study"
        label="Field of study"
      />
    );
  }

  renderSchool() {
    return (
      <Input
        ref="school"
        type="text"
        placeholder="School"
        label="School"
      />
    );
  }

  renderCity() {
    return (
      <Input
        ref="degree"
        type="text"
        placeholder="City"
        label="City"
      />
    );
  }

  renderCountry() {
    return (
      <Input
        ref="country"
        type="text"
        placeholder="Country"
        label="Country"
      />
    );
  }

  renderStartYear() {
    return (
      <Input
        ref="startYear"
        type="number"
        placeholder="Start year"
        label="Start year"
      />
    );
  }

  renderEndYear() {
    return (
      <Input
        ref="endYear"
        type="number"
        placeholder="End year"
        label="End year"
      />
    );
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
