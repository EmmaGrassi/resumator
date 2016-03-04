import React from 'react';
import { Button, Col, Input, Row } from 'react-bootstrap';

class PersonalForm extends React.Component {
  componentWillMount() {
    // Sets the passed values in the props from the parent component into the state.
    this.setState(this.props.values);
  }

  componentWillReceiveProps({ values }) {
    this.setState(values);
  }

  // TODO: Change state into props.
  // Use an action.
  handleChange(name, event) {
    this.props.handleChange(name, this.refs[name].getValue());
  }

  handleSubmit(event) {
    event.preventDefault();

    this.props.handleSubmit();
  }

  // TODO: Do not render these for users that can not see them.
  renderType() {
  }

  renderTitle() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'title';
    const inputLabel = 'Title';

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
    const inputLabel = 'Name';

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
    const inputLabel = 'Surname';

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
    const inputLabel = 'Email';

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
    const inputLabel = 'Phonenumber';

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
    const inputLabel = 'Current residence';

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
    const inputLabel = 'Date of birth';

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
    const inputLabel = 'Nationality';

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

  renderAboutMe() {
    const { isSaving, hasFailed, errors, values } = this.props;

    const inputName = 'aboutMe';
    const inputLabel = 'About me';

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
      <form onSubmit={this.handleSubmit.bind(this)}>
        <Row>
          <Col xs={12}>
            {this.renderTitle()}
          </Col>
        </Row>

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
            <Button
              bsStyle="primary"
              onClick={this.handleSubmit.bind(this)}
            >
              Save
            </Button>
          </Col>
        </Row>
      </form>
    );
  }
}

export default PersonalForm;
