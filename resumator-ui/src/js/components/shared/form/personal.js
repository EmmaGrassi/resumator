import React from 'react';
import { Button, Col, Input, Row, Alert } from 'react-bootstrap';

import nationalities from '../../../data/nationalities';
import types from '../../../data/types';
import labelize from '../../../helpers/labelize';

class PersonalForm extends React.Component {
  handleChange(name, event) {
    this.props.handleChange(name, this.refs[name].getValue());
  }

  renderFirstRow() {
    if (this.props.profile.item.admin) {
      return (<Row>
        <Col xs={6}>
          {this.renderType()}
        </Col>
        <Col xs={6}>
          {this.renderTitle()}
        </Col>
      </Row>);
    }

    return (<Row>
      <Col xs={12}>
        {this.renderTitle()}
      </Col>
    </Row>);
  }

  // TODO: Do not render these for users that can not see them.
  renderType() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'type';
    const inputLabel = labelize(inputName, '*');

    const props = {
      ref: inputName,
      type: 'select',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props}>
        {Object.keys(types).map((key, i) => <option key={i} value={key}>{types[key]}</option>)}
      </Input>
    );
  }

  renderTitle() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'title';
    const inputLabel = labelize(inputName, '*');

    const props = {
      ref: inputName,
      type: 'text',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} />
    );
  }

  renderName() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'name';
    const inputLabel = labelize(inputName, '*');

    const props = {
      ref: inputName,
      type: 'text',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} />
    );
  }

  renderSurname() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'surname';
    const inputLabel = labelize(inputName, '*');

    const props = {
      ref: inputName,
      type: 'text',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} />
    );
  }

  renderEmail() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'email';
    const inputLabel = labelize(inputName, '*');

    const props = {
      ref: inputName,
      type: 'email',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} />
    );
  }

  renderPhonenumber() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'phonenumber';
    const inputLabel = labelize(inputName, '*');

    const props = {
      ref: inputName,
      type: 'text',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} />
    );
  }

  renderCurrentResidence() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'currentResidence';
    const inputLabel = labelize(inputName, '*');

    const props = {
      ref: inputName,
      type: 'text',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} />
    );
  }

  renderGithub() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'github';
    const inputLabel = 'Github';

    const props = {
      ref: inputName,
      type: 'text',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} />
    );
  }

  renderLinkedin() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'linkedin';
    const inputLabel = 'Linkedin';

    const props = {
      ref: inputName,
      type: 'text',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} />
    );
  }

  renderDateOfBirth() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'dateOfBirth';
    const inputLabel = labelize(inputName, '*');

    const props = {
      ref: inputName,
      type: 'date',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} />
    );
  }

  renderNationality() {
    const { isSaving, hasFailed, errors } = this.props;

    const inputName = 'nationality';
    const inputLabel = labelize(inputName, '*');

    const props = {
      ref: inputName,
      type: 'select',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} >
        {Object.keys(nationalities).map((key, i) => <option key={i} value={key}>{nationalities[key]}</option>)}
      </Input>
    );
  }

  renderAboutMe() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'aboutMe';
    const inputLabel = labelize(inputName, '*');

    const props = {
      ref: inputName,
      type: 'textarea',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} />
    );
  }

  render() {
    return (
      <form onSubmit={this.props.handleSubmit}>
        {this.renderFirstRow()}
        <Row>
          <Col xs={6}>
            {this.renderName()}
          </Col>

          <Col xs={6}>
            {this.renderSurname()}
          </Col>
        </Row>

        <Row>
          <Col xs={6}>
            {this.renderEmail()}
          </Col>

          <Col xs={6}>
            {this.renderPhonenumber()}
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            {this.renderCurrentResidence()}
          </Col>
        </Row>

        <Row>
          <Col xs={6}>
            {this.renderGithub()}
          </Col>

          <Col xs={6}>
            {this.renderLinkedin()}
          </Col>
        </Row>

        <Row>
          <Col xs={6}>
            {this.renderDateOfBirth()}
          </Col>

          <Col xs={6}>
            {this.renderNationality()}
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            {this.renderAboutMe()}
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            <div className="pull-right">
              <Button
                bsStyle="danger"
                onClick={this.props.handleCancel}
              >
                Cancel
              </Button>
              <Button
                bsStyle="primary"
                onClick={this.props.handleSubmit}
              >
                Save
              </Button>
            </div>
          </Col>
        </Row>
      </form>
    );
  }
}

export default PersonalForm;
