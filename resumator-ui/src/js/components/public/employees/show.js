import Loader from 'react-loader';
import React from 'react';
import moment from 'moment';
import { Button, ButtonGroup, Col, Grid, Image, ListGroup, ListGroupItem, Row } from 'react-bootstrap';
import { bindAll, map } from 'lodash';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';

import actions from '../../../actions';

function normalizeString(string) {
  const words = string.split('_');
  const casedWords = map(words, (w) => {
    return `${w.substring(0, 1)}${w.substring(1).toLowerCase()}`;
  });

  return casedWords.join(' ');
}

function mapStateToProps(state) {
  return {
    employees: state.employees
  };
}

function mapDispatchToProps(dispatch) {
  return {
    fetchEmployee: (id) => dispatch(actions.employees.show(id)),
    navigateToEmployeesEdit: (id) => dispatch(pushPath(`/employees/${id}/edit`))
  }
}

class Show extends React.Component {
  componentWillMount() {
    this.props.fetchEmployee(this.props.params.userId)
  }

  handleEditButtonClick() {
    const data = this.props.employees.show.toJS();

    this.props.navigateToEmployeesEdit(data.item.id);
  }

  getCourses() {
    const data = this.props.employees.show.toJS();

    return (
      <Row>
        <Col xs={12}>
          <h2>Courses</h2>
          <ListGroup>
            {data.item.courses.map((v, i) => {
              let {
                name,
                year,
                description
              } = v;

              return (
                <ListGroupItem key={i}>
                  <strong>{name} ({year})</strong><br/>
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
    const data = this.props.employees.show.toJS();

    return (
      <Row>
        <Col xs={12}>
          <h2>Education</h2>
          <ListGroup>
            {data.item.education.map((v, i) => {
              let {
                city,
                country,
                degree,
                endYear,
                fieldOfStudy,
                school,
                startYear
              } = v;

              degree = normalizeString(degree);

              return (
                <ListGroupItem key={i}>
                  <strong>{degree} in {fieldOfStudy}</strong><br/>
                  {school} in {city}, {country}<br/>
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
    const data = this.props.employees.show.toJS();

    return (
      <Row>
        <Col xs={12}>
          <h2>Experience</h2>
          <div>
            {data.item.experience.map((v, i) => {
              let {
                city,
                companyName,
                country,
                endDate,
                methodologies,
                shortDescription,
                startDate,
                technologies,
                title
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
              if (i !== data.item.experience.length - 1) {
                hr = <hr/>;
              }

              return (
                <div key={i}>
                  <h3
                    style={{
                      marginTop: '10px'
                    }}
                  >
                    {title}
                  </h3>
                  <h4>{companyName} ({city}, {country})</h4>
                  {startYear} - {endYear} ({difference})<br/>
                  <br/>
                  <p>{shortDescription}</p>
                  <strong>Technologies:</strong> {technologies}<br/>
                  <strong>Methodologies:</strong> {methodologies}<br/>
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
    const data = this.props.employees.show.toJS();

    return (
      <Row>
        <Col xs={12}>
          <h2>Languages</h2>
          <div>
            {data.item.languages.map((v, i) => {
              let { name, proficiency } = v;

              let hr;
              if (i !== data.item.experience.length - 1) {
                hr = <hr/>;
              }

              proficiency = normalizeString(proficiency);

              return (
                <div key={i}>
                  <strong>{name} ({proficiency})</strong><br/>
                  {hr}
                </div>
              );
            })}
          </div>
        </Col>
      </Row>
    );
  }

  render() {
    const data = this.props.employees.show.toJS();

    const item = data.item;
    const isFetching = data.isFetching;

    const courses = this.getCourses();
    const education = this.getEducation();
    const experience = this.getExperience();
    const languages = this.getLanguages();

    let {
      aboutMe,
      currentResidence,
      dateOfBirth,
      email,
      github,
      id,
      linkedin,
      name,
      nationality,
      phonenumber,
      surname,
      title
    } = item;

    const docxURL = `/api/employees/${id}/docx`;

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
                  <Button bsStyle="primary" onClick={this.handleEditButtonClick.bind(this)}>Edit</Button>
                  <Button bsStyle="default" href={docxURL}>DOCX</Button>
                </ButtonGroup>
              </span>

              <h1>{title}</h1>

              <table>
                <tr>
                  <td
                    style={{
                      paddingRight: '15px'
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
                      paddingRight: '15px'
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
                      paddingRight: '15px'
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
                      paddingRight: '15px'
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
                      paddingRight: '15px'
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
                      paddingRight: '15px'
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
                      paddingRight: '15px'
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
                      paddingRight: '15px'
                    }}
                  >
                    <strong>Linkedin:</strong>
                  </td>
                  <td>
                    {linkedin}
                  </td>
                </tr>
              </table>
              <br/>
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
