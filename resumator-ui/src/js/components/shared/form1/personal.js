import React from 'react';
import { Button, Col, Input, Row } from 'react-bootstrap';

class PersonalForm extends React.Component {
  constructor(options) {
    super(options);

    this.state = {
      title: null,
      name: null,
      surname: null,
      email: null,
      phonenumber: null,
      currentResidence: null,
      github: null,
      linkedin: null,
      dateOfBirth: null,
      nationality: null,
      aboutMe: null,
    };
  }

  // Don't use state, use props instead. Save the complete state in the
  // .edit/.create item and use an action to update the store state whenever any
  // input changes value.
  handleChange(name, event) {
  }

  handleSubmit(event) {
    event.preventDefault();

    const values = {
      title: this.refs.title.getValue(),
      name: this.refs.name.getValue(),
      surname: this.refs.surname.getValue(),
      email: this.refs.email.getValue(),
      phonenumber: this.refs.phonenumber.getValue(),
      currentResidence: this.refs.currentResidence.getValue(),
      github: this.refs.github.getValue(),
      linkedin: this.refs.linkedin.getValue(),
      dateOfBirth: this.refs.dateOfBirth.getValue(),
      nationality: this.refs.nationality.getValue(),
      aboutMe: this.refs.aboutMe.getValue(),
    };

    this.props.handleSubmit(values);
  }

  // TODO: Do not render these for users that can not see them.
  renderType() {
  }

  renderTitle() {
    const { isSaving, hasFailed, errors, values } = this.props;

    if (hasFailed && errors.title) {
      return (
        <Input
          onChange={this.handleChange.bind(this, 'title')}

          ref="title"
          type="text"
          placeholder="Title"
          label="Title"

          bsStyle="error"
          help={errors.title}
          hasFeedback

          disabled={isSaving}
        />
      );
    }

    return (
      <Input
        ref="title"
        type="text"
        placeholder="Title"
        label="Title"
        disabled={isSaving}
      />
    );
  }

  renderName() {
    const { hasFailed, errors } = this.props;

    if (hasFailed) {
      if (errors.name) {
        return (
          <Input
            ref="name"
            type="text"
            placeholder="Name"
            label="Name"

            bsStyle="error"
            help={errors.name}
            hasFeedback
          />
        );
      }
    }
    return (
      <Input
        ref="name"
        type="text"
        placeholder="Name"
        label="Name"
      />
    );
  }

  renderSurname() {
    const { hasFailed, errors } = this.props;

    if (hasFailed) {
      if (errors.surname) {
        return (
          <Input
            ref="surname"
            type="text"
            placeholder="Surame"
            label="Surame"

            bsStyle="error"
            help={errors.surname}
            hasFeedback
          />
        );
      }
    }
    return (
      <Input
        ref="surname"
        type="text"
        placeholder="Surame"
        label="Surame"
      />
    );
  }

  renderEmail() {
    const { hasFailed, errors } = this.props;

    if (hasFailed) {
      if (errors.email) {
        return (
          <Input
            ref="email"
            type="email"
            placeholder="Email"
            label="Email"

            bsStyle="error"
            help={errors.email}
            hasFeedback
          />
        );
      }
    }
    return (
      <Input
        ref="email"
        type="email"
        placeholder="Email"
        label="Email"
      />
    );
  }

  renderPhonenumber() {
    const { hasFailed, errors } = this.props;

    if (hasFailed) {
      if (errors.phonenumber) {
        return (
          <Input
            ref="phonenumber"
            type="number"
            placeholder="Phonenumber"
            label="Phonenumber"

            bsStyle="error"
            help={errors.phonenumber}
            hasFeedback
          />
        );
      }
    }
    return (
      <Input
        ref="phonenumber"
        type="number"
        placeholder="Phonenumber"
        label="Phonenumber"
      />
    );
  }

  renderCurrentResidence() {
    const { hasFailed, errors } = this.props;

    if (hasFailed) {
      if (errors.currentResidence) {
        return (
          <Input
            ref="currentResidence"
            type="text"
            placeholder="Current residence"
            label="Current residence"

            bsStyle="error"
            help={errors.currentResidence}
            hasFeedback
          />
        );
      }
    }
    return (
      <Input
        ref="currentResidence"
        type="text"
        placeholder="Current residence"
        label="Current residence"
      />
    );
  }

  renderGithub() {
    const { hasFailed, errors } = this.props;

    if (hasFailed) {
      if (errors.github) {
        return (
          <Input
            ref="github"
            type="text"
            placeholder="Github"
            label="Github"

            bsStyle="error"
            help={errors.github}
            hasFeedback
          />
        );
      }
    }
    return (
      <Input
        ref="github"
        type="text"
        placeholder="Github"
        label="Github"
      />
    );
  }

  renderLinkedin() {
    const { hasFailed, errors } = this.props;

    if (hasFailed) {
      if (errors.linkedin) {
        return (
          <Input
            ref="linkedin"
            type="text"
            placeholder="Linkedin"
            label="Linkedin"

            bsStyle="error"
            help={errors.linkedin}
            hasFeedback
          />
        );
      }
    }
    return (
      <Input
        ref="linkedin"
        type="text"
        placeholder="Linkedin"
        label="Linkedin"
      />
    );
  }

  renderDateOfBirth() {
    const { hasFailed, errors } = this.props;

    if (hasFailed) {
      if (errors.dateOfBirth) {
        return (
          <Input
            ref="dateOfBirth"
            type="date"
            placeholder="Date of birth"
            label="Date of birth"

            bsStyle="error"
            help={errors.dateOfBirth}
            hasFeedback
          />
        );
      }
    }
    return (
      <Input
        ref="dateOfBirth"
        type="date"
        placeholder="Date of birth"
        label="Date of birth"
      />
    );
  }

  renderNationality() {
    const { hasFailed, errors } = this.props;

    if (hasFailed) {
      if (errors.nationality) {
        return (
          <Input
            ref="nationality"
            type="text"
            placeholder="Nationality"
            label="Nationality"

            bsStyle="error"
            help={errors.nationality}
            hasFeedback
          />
        );
      }
    }
    return (
      <Input
        ref="nationality"
        type="text"
        placeholder="Nationality"
        label="Nationality"
      />
    );
  }

  renderAboutMe() {
    const { hasFailed, errors } = this.props;

    if (hasFailed) {
      if (errors.aboutMe) {
        return (
          <Input
            ref="aboutMe"
            type="textarea"
            placeholder="About me"
            label="About me"

            bsStyle="error"
            help={errors.aboutMe}
            hasFeedback
          />
        );
      }
    }
    return (
      <Input
        ref="aboutMe"
        type="textarea"
        placeholder="About me"
        label="About me"
      />
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
