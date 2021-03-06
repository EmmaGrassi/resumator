import Loader from 'react-loader';
import React from 'react';
import moment from 'moment';
import { bindAll, map } from 'lodash';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';
import convertCountry from '../../../../helpers/convertCountry';
import convertProficiency from '../../../../helpers/convertProficiency';
import convertNationality from '../../../../helpers/convertNationality';
import labelize from '../../../../helpers/labelize';
import isUpperCase from '../../../../helpers/isUpperCase';
import intersperse from '../../../../helpers/intersperse';

import CommentPane from '../../../shared/comment/CommentPane';

import {
  Button,
  ButtonGroup,
  Col,
  Grid,
  Image,
  ListGroup,
  ListGroupItem,
  Row,
  Badge,
} from 'react-bootstrap';

import actions from '../../../../actions';

function normalizeString(string) {
  const words = string.split('_');
  const casedWords = map(words, (w) => `${w.substring(0, 1)}${w.substring(1).toLowerCase()}`);
  return casedWords.join(' ');
}

function getDateDiff(a, b) {
  const c = b.diff(a, 'years');
  return (c < 1) ? `${b.diff(a, 'months')} months` : `${c} years`;
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

  handleEditButtonClick() {
    this.props.navigateToEdit(this.props.show.item.email);
  }

  renderHeader() {
    const { role } = this.props.show.item;
    return (<header>
      <div className="role">{role}</div>
      <div className="image-container">
        <img src="/images/sytac.png" />
      </div>
    </header>);
  }

  renderProfile() {
    const item = this.props.show.item;
    const {
      aboutMe,
      name,
      surname,
      nationality,
      email,
      dateOfBirth,
      phonenumber,
      github,
      linkedin,
    } = item;

    const profileData = {
      name: `${name} ${surname}`,
      dateOfBirth: moment(dateOfBirth).format('DD MMMM, YYYY'),
      yearOfBirth: moment(dateOfBirth).format('YYYY'),
      nationality,
      email,
      phone: phonenumber,
      github,
      linkedin,
    };

    return (<div className="section profile">
      <div className="section-content">
        {Object.keys(profileData).map(k => {
          const key = labelize(k);
          const value = (k === 'nationality') ?
            convertNationality(profileData[k]) : profileData[k];

          return (<div className={`content-row ${k}`} key={k}>
                    <div className="key">{key}:</div>
                    <div className="value">{value}</div>
                  </div>);
        })}
        <div className="description" dangerouslySetInnerHTML={{ __html: aboutMe }} />
      </div>
    </div>);
  }

  renderExperience() {
    const exp = this.props.show.item.experience;
    if (exp.length === 0) return this.renderNoData('Education');
    return (<div className="section experience">
      <div className="section-header">Experience</div>
      <div className="section-content">
      {exp.map((v, i) => {
        const {
          city,
          companyName,
          country,
          shortDescription,
          role,
        } = v;

        let {
          startDate,
          endDate,
          technologies,
          methodologies,
        } = v;
        let difference;
        let endYear;

        startDate = moment(startDate);
        const startYear = startDate.format('MMM. YYYY');
        if (endDate) {
          endDate = moment(endDate);
          difference = getDateDiff(startDate, endDate);
          endYear = endDate.format('MMM. YYYY');
        } else {
          difference = getDateDiff(startDate, moment());
          endYear = 'present';
        }
        // <div className="date">{startYear} - {endYear} ({difference})</div>

        technologies = intersperse(technologies
          .map((x, i) => <Badge key={i}>{x}</Badge>), ' ');
        methodologies = intersperse(methodologies
          .map((x, i) => <Badge key={i}>{x}</Badge>), ' ');

        return (
          <div className="list-item" key={i}>
            <div className="date">{startYear} - {endYear}</div>
            <div className="role">{role}</div>
            <small>{companyName} ({city}, {convertCountry(country)})</small>
            <div
              className="description"
              dangerouslySetInnerHTML={{ __html: shortDescription }}
            />
            <div className="skill-list-container">
              <div className="skill-list content-row">
                <span className="list-title key">Technologies:</span>
                <span className="value">{technologies}</span>
              </div>
              <div className="skill-list content-row">
                <span className="list-title key">Methodologies:</span>
                <span className="value">{methodologies}</span>
              </div>
            </div>
          </div>
        );
      })}
      </div>
    </div>);
  }

  renderEducation() {
    const edus = this.props.show.item.education;
    if (edus.length === 0) return this.renderNoData('Education');
    return (<div className="section education all-in-new-page">
      <div className="section-header">Education</div>
      <div className="section-content">
          {edus.map((v, i) => {
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
            let yearsSpend = Math.abs(startYear - endYear);
            yearsSpend = `${yearsSpend} ${yearsSpend > 1 ? 'years' : 'year'}`;
            // <div className="date">{startYear} - {endYear} ({yearsSpend})</div>

            return (<div className="list-item" key={`${i}_edu`}>
              <div className="date">{startYear} - {endYear}</div>
              <div className="degree">
              {v.degree === 'OTHER' ?
                <strong>{v.otherDegree} in {fieldOfStudy}</strong> :
                <strong>{degree} in {fieldOfStudy}</strong>}
              </div>

              <small>{school} ({city}, {convertCountry(country)})</small>
            </div>);
          })}
        </div>
      </div>
    );
  }

  renderLanguages() {
    const langs = this.props.show.item.languages;
    if (langs.length === 0) return this.renderNoData('Languages');
    return (<div className="section languages all-in-new-page">
      <div className="section-header">Languages</div>
      <div className="section-content list-item">
          {langs.map((v, i) => {
            const { name, proficiency } = v;
            return (<div className="content-row" key={`${i}_lang`}>
                      <div className="key">{name}:</div>
                      <div className="value">{convertProficiency(proficiency)}</div>
                    </div>);
          })}
        </div>
      </div>
    );
  }

  renderCourses() {
    const courses = this.props.show.item.courses;
    if (courses.length === 0) return this.renderNoData('Courses');
    return (<div className="section courses all-in-new-page">
      <div className="section-header">Courses</div>
      <div className="section-content">
          {courses.map((v, i) => {
            const {
              name,
              year,
              description,
            } = v;

            return (<div className="list-item" key={i}>
              <div className="date">{year}</div>
              <div className="role">{name}</div>
              <div
                className="description"
                dangerouslySetInnerHTML={{ __html: description }}
              />
            </div>);
          })}
        </div>
      </div>);
  }

  renderNoData(section) {
    return (<div className="section noprint">
      <div className="section-header">{section}</div>
      <div className="section-content">
        No data for {section.toLowerCase()}
      </div>
    </div>);
  }

  render() {
    const isFetching = this.props.show.isFetching;
    return (
      <Loader
        loaded={!isFetching}
      >
        <div className="resume-container">
          {this.renderHeader()}
          {this.renderProfile()}
          {this.renderExperience()}
          {this.renderEducation()}
          {this.renderLanguages()}
          {this.renderCourses()}
        </div>
        <div className="tools noprint">
          <ButtonGroup>
            <Button bsStyle="primary" onClick={this.handleEditButtonClick.bind(this)}>
              Edit
            </Button>
            <Button bsStyle="default" onClick={window.print}>
              PDF
            </Button>
          </ButtonGroup>
        </div>
      </Loader>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Show);
