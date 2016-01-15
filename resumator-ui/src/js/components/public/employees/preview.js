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
    preview: state.employees.get('preview')
  };
}

function mapDispatchToProps(dispatch) {
  return {
    fetchEmployee: (id) => dispatch(actions.employees.preview(id)),
    navigateToEmployeesEdit: (id) => dispatch(pushPath(`/employees/${id}/edit`))
  }
}

class Preview extends React.Component {
  componentWillMount() {
    this.props.fetchEmployee(this.props.params.userId)
  }

  handleEditButtonClick() {
    const item = this.props.preview.get('item');

    this.props.navigateToEmployeesEdit(item.get('id'));
  }

  renderProfile() {
    const item = this.props.preview.get('item');

    const yearOfBirth = item.get('dateOfBirth') && moment(item.get('dateOfBirth')).format('YYYY') || null;
    const email = item.get('email');
    const github = item.get('github');
    const linkedin = item.get('linkedin');
    const name = item.get('name');
    const nationality = item.get('nationality');
    const phonenumber = item.get('phonenumber');
    const surname = item.get('surname');

    return (
      <div>
        <Row>
          <Col xs={3}>
            <h3
              style={{
                marginTop: '0',
                marginBottom: '0'
              }}
            >
              Profile
            </h3>
          </Col>

          <Col xs={9}>
            <strong>Name:</strong> {name} {surname}<br/>
            <strong>Year of Birth:</strong> {yearOfBirth}<br/>
            <strong>Nationality:</strong> {nationality}<br/>
          </Col>
        </Row>
      </div>
    )
  }

  renderExperience() {
    const item = this.props.preview.get('item');
    const experience = item.get('experience');

    return (
      <div>
        <Row>
          <Col xs={3}>
            <h3
              style={{
                marginTop: '0',
                marginBottom: '0'
              }}
            >
              Experience
            </h3>
          </Col>

          <Col xs={9}>
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
                <div
                  key={i}
                  style={{
                    marginBottom: '30px'
                  }}
                >
                  <div className="pull-right">
                    {startDate.format('YYYY')} - {endDate.format('YYYY')}
                  </div>
                  <h4>{v.get('title')}</h4>
                  <h5>{v.get('companyName')} - {v.get('location')}</h5>
                  <p>{v.get('shortDescription')}</p>

                  <Row>
                    <Col xs={3}>
                      <strong>Technologies:</strong>
                    </Col>
                    <Col xs={9}>
                      {v.get('technologies').join(', ')}
                    </Col>
                  </Row>

                  <Row>
                    <Col xs={3}>
                      <strong>Methodologies:</strong>
                    </Col>
                    <Col xs={9}>
                      {v.get('methodologies').join(', ')}
                    </Col>
                  </Row>
                </div>
              );
            })}
          </Col>
        </Row>
      </div>
    );
  }

  renderEducation() {
    const item = this.props.preview.get('item');
    const education = item.get('education');

    return (
      <div>
        <Row>
          <Col xs={3}>
            <h3
              style={{
                marginTop: '0',
                marginBottom: '0'
              }}
            >
              Education
            </h3>
          </Col>

          <Col xs={9}>
            {education.map((v, i) => {
              return (
                <div
                  key={i}
                  style={{
                    marginBottom: '30px'
                  }}
                >
                  <div className="pull-right">
                    {v.get('graduationYear')}
                  </div>
                  <h4>{v.get('degree')} in {v.get('fieldOfStudy')}</h4>
                  <h6>{v.get('university')}</h6>
                </div>
              );
            })}
          </Col>
        </Row>
      </div>
    );
  }

  renderCourses() {
    const item = this.props.preview.get('item');
    const courses = item.get('courses');

    return (
      <div>
        <Row>
          <Col xs={3}>
            <h3
              style={{
                marginTop: '0',
                marginBottom: '0'
              }}
            >
              Courses / Workshops
            </h3>
          </Col>

          <Col xs={9}>
            {courses.map((v, i) => {
              const name = v.get('name');
              const date = v.get('date') && moment(v.get(date)).format('YYYY');
              const description = v.get('description');

              return (
                <div key={i}>
                  <div className="pull-right">{date}</div>
                  <div>
                    <strong>{name}</strong>
                  </div>
                </div>
              );
            })}
          </Col>
        </Row>
      </div>
    );
  }

  renderLanguages() {
    const item = this.props.preview.get('item');
    const languages = item.get('languages');

    return (
      <div>
        <Row
          style={{
            marginBottom: '25px'
          }}
        >
          <Col xs={3}>
            <h3
              style={{
                marginTop: '0',
                marginBottom: '0'
              }}
            >
              Languages
            </h3>
          </Col>

          <Col xs={9}>
            {languages.map((v, i) => {
              const name = v.get('name');
              const words = v.get('proficiency').split('_');
              const casedWords = map(words, (w) => {
                return `${w.substring(0, 1)}${w.substring(1).toLowerCase()}`;
              });
              const proficiency = casedWords.join(' ');

              return (
                <div key={i}>
                  <div className="pull-right">{proficiency}</div>
                  <div>
                    <strong>{name}</strong>
                  </div>
                </div>
              );
            })}
          </Col>
        </Row>
      </div>
    );
  }

  render() {
    const item = this.props.preview.get('item');
    const isFetching = this.props.preview.get('isFetching');
    const title = item.get('title');

    return (
      <Loader
        loaded={!isFetching}
      >
        <Grid>
          <Row>
            <Col xs={3}>
              <Image
                src="/images/sytac.png"
                className="pull-right"
                style={{
                  marginTop: '17px'
                }}
              />
            </Col>

            <Col xs={9}>
              <h2>{title}</h2>
            </Col>
          </Row>
          <hr
            style={{

            }}
          />
          {this.renderProfile()}
          <hr/>
          {this.renderExperience()}
          <hr/>
          {this.renderEducation()}
          <hr/>
          {this.renderCourses()}
          <hr/>
          {this.renderLanguages()}
        </Grid>
      </Loader>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Preview);
