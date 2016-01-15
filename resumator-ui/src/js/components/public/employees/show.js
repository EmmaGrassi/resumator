import Loader from 'react-loader';
import React from 'react';
import moment from 'moment';
import { Button, Col, Grid, Image, ListGroup, ListGroupItem, Row } from 'react-bootstrap';
import { bindAll, map } from 'lodash';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';

import actions from '../../../actions';

function mapStateToProps(state) {
  return {
    show: state.employees.get('show')
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
    const item = this.props.show.get('item');

    this.props.navigateToEmployeesEdit(item.get('id'));
  }

  getCourses() {
    const item = this.props.show.get('item');
    const courses = item.get('courses');

    return (
      <ListGroup>
        {courses.map((v, i) => {
          const name = v.get('name');
          const date = v.get('date') && moment(v.get(date)).format('YYYY');
          const description = v.get('description');

          return (
            <ListGroupItem key={i}>
              <strong>{name} ({date}))</strong><br/>
              {description}
            </ListGroupItem>
          );
        })}
      </ListGroup>
    );
  }

  getEducation() {
    const item = this.props.show.get('item');
    const education = item.get('education');

    return (
      <ListGroup>
        {education.map((v, i) => {
          return (
            <ListGroupItem key={i}>
              <strong>{v.get('degree')}</strong><br/>
              {v.get('university')} ({v.get('fieldOfStudy')})<br/>
              {v.get('graduated') && `Graduated in ${v.get('graduationYear')}`}
            </ListGroupItem>
          );
        })}
      </ListGroup>
    );
  }

  getExperience() {
    const item = this.props.show.get('item');
    const experience = item.get('experience');

    return (
      <ListGroup>
        {experience.map((v, i) => {
          const startDate = moment(v.get('startDate'));
          const endDate = moment(v.get('endDate'));

          let difference = endDate.diff(startDate, 'years');

          if (difference < 1) {
            difference = `${endDate.diff(startDate, 'months')} months`;
          } else {
            difference = `${difference} years`;
          }

          return (
            <ListGroupItem key={i}>
              <strong>{v.get('title')}</strong> at {v.get('companyName')} ({v.get('location')})<br/>
              {startDate.format('YYYY')} - {endDate.format('YYYY')} ({difference})<br/>
              <br/>
              <p>{v.get('shortDescription')}</p>
              <strong>Technologies:</strong> {v.get('technologies').join(', ')}<br/>
              <strong>Methodologies:</strong> {v.get('methodologies').join(', ')}<br/>
            </ListGroupItem>
          );
        })}
      </ListGroup>
    );
  }

  getLanguages() {
    const item = this.props.show.get('item');
    const languages = item.get('languages');

    return (
      <ListGroup>
        {languages.map((v, i) => {
          const words = v.get('proficiency').split('_');
          const casedWords = map(words, (w) => {
            return `${w.substring(0, 1)}${w.substring(1).toLowerCase()}`;
          });
          const proficiency = casedWords.join(' ');

          return (
            <ListGroupItem key={i}>
              <strong>{v.get('name')} ({proficiency})</strong><br/>
            </ListGroupItem>
          );
        })}
      </ListGroup>
    );
  }

  render() {
    const item = this.props.show.get('item');
    const isFetching = this.props.show.get('isFetching');

    const dateOfBirth = item.get('dateOfBirth') && moment(item.get('dateOfBirth')).format('YYYY-MM-DD') || null;
    const email = item.get('email');
    const github = item.get('github');
    const linkedin = item.get('linkedin');
    const name = item.get('name');
    const nationality = item.get('nationality');
    const phonenumber = item.get('phonenumber');
    const surname = item.get('surname');
    const title = item.get('title');

    const courses = this.getCourses();
    const education = this.getEducation();
    const experience = this.getExperience();
    const languages = this.getLanguages();

    return (
      <Loader
        loaded={!isFetching}
      >
        <Grid>
          <Row>
            <Col xs={4}>
              <Image src="/images/thumbnail.png" circle />
            </Col>

            <Col xs={8}>
              <span className="pull-right">
                <Button bsStyle="primary" onClick={this.handleEditButtonClick.bind(this)}>Edit</Button>
              </span>

              <h1>{title}</h1>

              <strong>Name:</strong> {name} {surname}<br/>
              <strong>Nationality:</strong> {nationality}<br/>
              <strong>Date of Birth:</strong> {dateOfBirth}<br/>
              <strong>Email:</strong> {email}<br/>
              <strong>Phone:</strong> {phonenumber}<br/>
              <strong>Github:</strong> {github}<br/>
              <strong>Linkedin:</strong> {linkedin}<br/>
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
      </Loader>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Show);
