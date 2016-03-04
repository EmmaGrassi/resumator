import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

class ExperienceForm extends React.Component {
  renderTitle() {
    return (
      <Input
        ref="title"
        type="text"
        placeholder="Title"
        label="Title"
      />
    );
  }

  renderCompanyName() {
    return (
      <Input
        ref="companyName"
        type="text"
        placeholder="Company Name"
        label="Company Name"
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

  renderStartDate() {
    return (
      <Input
        ref="startDate"
        type="date"
        placeholder="Start date"
        label="Start date"
      />
    );
  }

  renderEndDate() {
    return (
      <Input
        ref="endDate"
        type="date"
        placeholder="End date"
        label="End date"
      />
    );
  }

  renderShortDescription() {
    return (
      <Input
        ref="shortDescription"
        type="textarea"
        placeholder="Short description"
        label="Short description"
      />
    );
  }

  renderTechnologies() {
    return (
      <Input
        ref="technologies"
        type="text"
        placeholder="Technologies"
        label="Technologies"
      />
    );
  }

  renderMethodologies() {
    return (
      <Input
        ref="methodologies"
        type="text"
        placeholder="Methodologies"
        label="Methodologies"
      />
    );
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
