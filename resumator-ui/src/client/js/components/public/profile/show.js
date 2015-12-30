const React = require('react');
const moment = require('moment');
const { bindAll, map } = require('lodash');
const { connect } = require('react-redux');

const {
  Button,
  Col,
  Grid,
  Image,
  ListGroup,
  ListGroupItem,
  Row
} = require('react-bootstrap');

class Show extends React.Component {
  constructor(options) {
    super(options);

    bindAll(this, [
      'handleEditButtonClick'
    ])
  }

  getExperience() {
    const experience = this.props.user.item.experience;
    const listItems = map(experience, (v) => {
      return (
        <ListGroupItem>
          <strong>{v.title}</strong> at {v.companyName}<br/>
          {moment(v.startDate).format('YYYY')} - {moment(v.endDate).format('YYYY')} ({v.location})<br/>
          <br/>
          <strong>Technologies:</strong><br/>
          <ul>
            {map(v.technologies, (v) => <li>{v}</li>)}
          </ul>
          <br/>
          <strong>Methodologies:</strong><br/>
          <ul>
            {map(v.methodologies, (v) => <li>{v}</li>)}
          </ul>
        </ListGroupItem>
      );
    });

    return (
      <ListGroup>
        {listItems}
      </ListGroup>
    );
  }

  getEducation() {
    const education = this.props.user.item.education;
    const listItems = map(education, (v) => {
      return (
        <ListGroupItem>
          <strong>{v.degree}</strong><br/>
          {v.university} ({v.fieldOfStudy})<br/>
          {v.graduated && `Graduated in ${v.graduationYear}`}
        </ListGroupItem>
      );
    });

    return (
      <ListGroup>
        {listItems}
      </ListGroup>
    );
  }

  getCourses() {
    const courses = this.props.user.item.courses;
    const listItems = map(courses, (v) => {
      return (
        <ListGroupItem>
          <strong>{v.name} ({moment(v.date).format('YYYY')})</strong><br/>
          {v.description}
        </ListGroupItem>
      );
    });

    return (
      <ListGroup>
        {listItems}
      </ListGroup>
    );
  }

  getLanguages() {
    const items = this.props.user.item.languages;
    const listItems = map(items, (v) => {
      const words = v.proficiency.split('_');
      const casedWords = map(words, (w) => {
        return `${w.substring(0, 1)}${s.substring(1).toLowerCase()}`;
      });

      return (
        <ListGroupItem>
          <strong>{v.name} ({v.proficiency})</strong><br/>
        </ListGroupItem>
      );
    });

    return (
      <ListGroup>
        {listItems}
      </ListGroup>
    );
  }

  render() {
    const data = this.props.user.item;

    const experience = this.getExperience();
    const education = this.getEducation();
    const courses = this.getCourses();
    const languages = this.getLanguages();

    return (
      <Grid fluid={true}>
        <Row>
          <Col xs={4}>
            <Image src="/images/thumbnail.png" circle />
          </Col>

          <Col xs={8}>
            <span className="pull-right">
              <Button bsStyle="primary" onClick={this.handleEditButtonClick}>Edit</Button>
            </span>

            <h1>{data.title}</h1>

            <strong>Name:</strong> {data.name} {data.surname}<br/>
            <strong>Nationality:</strong> {data.nationality}<br/>
            <strong>Date of Birth:</strong> {moment(data.dateOfBirth).format('YYYY-MM-DD')}<br/>
            <strong>Email:</strong> {data.email}<br/>
            <strong>Phone:</strong> {data.phonenumber}<br/>
            <strong>Github:</strong> {data.github}<br/>
            <strong>Linkedin:</strong> {data.linkedin}<br/>
          </Col>
        </Row>

        <Row>
          <Col xsOffset={4} xs={8}>
            <h3>Experience</h3>

            {experience}
          </Col>
        </Row>

        <Row>
          <Col xsOffset={4} xs={8}>
            <h3>Education</h3>

            {education}
          </Col>
        </Row>

        <Row>
          <Col xsOffset={4} xs={8}>
            <h3>Courses</h3>

            {courses}
          </Col>
        </Row>

        <Row>
          <Col xsOffset={4} xs={8}>
            <h3>Languages</h3>

            {languages}
          </Col>
        </Row>
      </Grid>
    );
  }

  handleEditButtonClick() {
    const { id } = this.props.user;

    window.location.hash = `${id}/edit`;
  }
}

Show.displayName = 'Show';

function select(state) {
  return {
    user: state.user
  };
}

module.exports = connect(select)(Show);
