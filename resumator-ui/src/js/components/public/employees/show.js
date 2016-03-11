import Loader from 'react-loader';
import React from 'react';
import moment from 'moment';
import { bindAll, map } from 'lodash';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';

import {
  Button,
  ButtonGroup,
  Col,
  Grid,
  Image,
  ListGroup,
  ListGroupItem,
  Row,
} from 'react-bootstrap';

import actions from '../../../actions';

function normalizeString(string) {
  const words = string.split('_');
  const casedWords = map(words, (w) => `${w.substring(0, 1)}${w.substring(1).toLowerCase()}`);
  return casedWords.join(' ');
}

function mapStateToProps(state) {
  return {
    show: state.employees.show.toJS(),
  };
}

function mapDispatchToProps(dispatch) {
  return {
    fetchEmployee: (email) => dispatch(actions.employees.show(email)),
    navigateToEdit: (email) => dispatch(pushPath(`/employees/${email}/edit`)),
  };
}

class Show extends React.Component {
  componentWillMount() {
    this.props.fetchEmployee(this.props.params.userId);
  }

  getCourses() {
    return (
      <Row>
        <Col xs={12}>
          <h2>Courses</h2>
          <ListGroup>
            {this.props.show.item.courses.map((v, i) => {
              const {
                name,
                year,
                description,
              } = v;

              return (
                <ListGroupItem key={i}>
                  <strong>{name} ({year})</strong><br />
                  {description}
                </ListGroupItem>
              );
            })}
          </ListGroup>
        </Col>
      </Row>
    );
  }

  getEducation() {
    return (
      <Row>
        <Col xs={12}>
          <h2>Education</h2>
          <ListGroup>
            {this.props.show.item.education.map((v, i) => {
              const {
                city,
                country,
                endYear,
                fieldOfStudy,
                school,
                startYear,
              } = v;

              let { degree } = v;
              degree = normalizeString(degree);

              return (
                <ListGroupItem key={i}>
                  <strong>{degree} in {fieldOfStudy}</strong><br />
                  {school} in {city}, {country}<br />
                  {startYear} - {endYear}
                </ListGroupItem>
              );
            })}
          </ListGroup>
        </Col>
      </Row>
    );
  }

  getExperience() {
    return (
      <Row>
        <Col xs={12}>
          <h2>Experience</h2>
          <div>
            {this.props.show.item.experience.map((v, i) => {
              const {
                city,
                companyName,
                country,
                shortDescription,
                title,
              } = v;

              let {
                startDate,
                endDate,
                technologies,
                methodologies,
              } = v;

              startDate = moment(startDate);
              endDate = moment(endDate);

              let difference = endDate.diff(startDate, 'years');

              if (difference < 1) {
                difference = `${endDate.diff(startDate, 'months')} months`;
              } else {
                difference = `${difference} years`;
              }

              const startYear = startDate.format('YYYY');
              const endYear = endDate.format('YYYY');

              technologies = technologies.join(', ');
              methodologies = methodologies.join(', ');

              let hr;
              if (i !== this.props.show.item.experience.length - 1) {
                hr = <hr />;
              }

              return (
                <div key={i}>
                  <h3
                    style={{
                      marginTop: '10px',
                    }}
                  >
                    {title}
                  </h3>
                  <h4>{companyName} ({city}, {country})</h4>
                  {startYear} - {endYear} ({difference})<br />
                  <br />
                  <p>{shortDescription}</p>
                  <strong>Technologies:</strong> {technologies}<br />
                  <strong>Methodologies:</strong> {methodologies}<br />
                  {hr}
                </div>
              );
            })}
          </div>
        </Col>
      </Row>
    );
  }

  getLanguages() {
    return (
      <Row>
        <Col xs={12}>
          <h2>Languages</h2>
          <div>
            {this.props.show.item.languages.map((v, i) => {
              let { proficiency } = v;
              const { name } = v;

              let hr;
              if (i !== this.props.show.item.experience.length - 1) {
                hr = <hr />;
              }

              proficiency = normalizeString(proficiency);

              return (
                <div key={i}>
                  <strong>{name} ({proficiency})</strong><br />
                  {hr}
                </div>
              );
            })}
          </div>
        </Col>
      </Row>
    );
  }

  handleEditButtonClick() {
    this.props.navigateToEdit(this.props.show.item.email);
  }

  render() {
    const item = this.props.show.item;
    const isFetching = this.props.show.isFetching;

    const courses = this.getCourses();
    const education = this.getEducation();
    const experience = this.getExperience();
    const languages = this.getLanguages();

    const {
      aboutMe,
      currentResidence,
      email,
      github,
      id,
      linkedin,
      name,
      phonenumber,
      surname,
      title,
    } = item;
    let { nationality, dateOfBirth } = item;

    const docxURL = `/api/employees/${email}/docx`;

    nationality = nationality && normalizeString(nationality);
    dateOfBirth = moment(dateOfBirth).format('YYYY-MM-DD');

    return (
      <Loader
        loaded={!isFetching}
      >
        <Grid>
          <Row>
            <Col xs={12}>
              <span className="pull-right">
                <ButtonGroup>
                  <Button bsStyle="primary" onClick={this.handleEditButtonClick.bind(this)}>
                    Edit
                  </Button>
                  <Button bsStyle="default" href={docxURL} download>DOCX</Button>
                </ButtonGroup>
              </span>

              <h1>{title}</h1>

              <table>
                <tbody>
                  <tr>
                    <td
                      style={{
                        paddingRight: '15px',
                      }}
                    >
                      <strong>Name:</strong>
                    </td>
                    <td>
                      {name} {surname}
                    </td>
                  </tr>

                  <tr>
                    <td
                      style={{
                        paddingRight: '15px',
                      }}
                    >
                      <strong>Nationality:</strong>
                    </td>
                    <td>
                      {nationality}
                    </td>
                  </tr>

                  <tr>
                    <td
                      style={{
                        paddingRight: '15px',
                      }}
                    >
                      <strong>Date of Birth:</strong>
                    </td>
                    <td>
                      {dateOfBirth}
                    </td>
                  </tr>

                  <tr>
                    <td
                      style={{
                        paddingRight: '15px',
                      }}
                    >
                      <strong>Email:</strong>
                    </td>
                    <td>
                      {email}
                    </td>
                  </tr>

                  <tr>
                    <td
                      style={{
                        paddingRight: '15px',
                      }}
                    >
                      <strong>Phone:</strong>
                    </td>
                    <td>
                      {phonenumber}
                    </td>
                  </tr>

                  <tr>
                    <td
                      style={{
                        paddingRight: '15px',
                      }}
                    >
                      <strong>Current residence:</strong>
                    </td>
                    <td>
                      {currentResidence}
                    </td>
                  </tr>

                  <tr>
                    <td
                      style={{
                        paddingRight: '15px',
                      }}
                    >
                      <strong>Github:</strong>
                    </td>
                    <td>
                      {github}
                    </td>
                  </tr>

                  <tr>
                    <td
                      style={{
                        paddingRight: '15px',
                      }}
                    >
                      <strong>Linkedin:</strong>
                    </td>
                    <td>
                      {linkedin}
                    </td>
                  </tr>
                </tbody>
              </table>
              <br />
              <p>{aboutMe}</p>
            </Col>
          </Row>

          {experience}
          {education}
          {languages}
          {courses}
        </Grid>
      </Loader>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Show);
