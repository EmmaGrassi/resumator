const React = require('react');
const moment = require('moment');
const { bindAll, map } = require('lodash');
const { connect } = require('react-redux');

const {
  Button,
  Col,
  Grid,
  Row
} = require('react-bootstrap');

class Preview extends React.Component {
  constructor(options) {
    super(options);

    bindAll(this, [
      'createPDF'
    ]);
  }

  getExperience() {
    const experience = this.props.user.new.experience;
    const listItems = map(experience, (v) => {
      const startDate = moment(v.startDate);
      const endDate = moment(v.endDate);

      let difference = endDate.diff(startDate, 'years');

      if (difference < 1) {
        difference = `${endDate.diff(startDate, 'months')} months`;
      } else {
        difference = `${difference} years`;
      }

      return (
        <div
          style={{
            marginTop: '20px'
          }}
        >
        <div className="pull-right">{moment(v.startDate).format('YYYY')} - {moment(v.endDate).format('YYYY')} ({difference})</div>
          <h4>{v.title}</h4>
          <h5>{v.companyName} - ({v.location})</h5>
          <p>{v.shortDescription}</p>
          <strong>Technologies:</strong> {v.technologies.join(', ')}<br/>
          <strong>Methodologies:</strong> {v.methodologies.join(', ')}<br/>
        </div>
      );
    });

    return (
      <div>
        {listItems}
      </div>
    );
  }

  getEducation() {
    const education = this.props.user.new.education;
    const listItems = map(education, (v) => {
      return (
        <div
          style={{
            marginBottom: '10px'
          }}
        >
          <strong>{v.degree}</strong><br/>
          {v.university} ({v.fieldOfStudy})<br/>
          {v.graduated && `Graduated in ${v.graduationYear}`}
        </div>
      );
    });

    return (
      <div>
        {listItems}
      </div>
    );
  }

  getCourses() {
    const courses = this.props.user.new.courses;
    const listItems = map(courses, (v) => {
      return (
        <div
          style={{
            marginBottom: '10px'
          }}
        >
          <strong>{v.name} ({moment(v.date).format('YYYY')})</strong><br/>
          {v.description}
        </div>
      );
    });

    return (
      <div>
        {listItems}
      </div>
    );
  }

  getLanguages() {
    const items = this.props.user.item.languages;
    const listItems = map(items, (v) => {
      const words = v.proficiency.split('_');
      const casedWords = map(words, (w) => {
        return `${w.substring(0, 1)}${w.substring(1).toLowerCase()}`;
      });
      const proficiency = casedWords.join(' ');

      return (
        <div>
          <strong>{v.name}</strong> ({proficiency})<br/>
        </div>
      );
    });

    return (
      <div>
        {listItems}
      </div>
    );
  }

  render() {
    const data = this.props.user.new;

    const nationality = `${data.nationality.substring(0, 1)}${data.nationality.substring(1).toLowerCase()}`;

    return (
      <Grid>
        <Row>
          <Col xs={12}>
            <div className="pull-right">
              <Button
                bsStyle="primary"
                onClick={this.createPDF}
              >
                Create PDF
              </Button>
            </div>
          </Col>
        </Row>
        <Row>
          <Col xs={12} ref="content">
            <h1
              style={{
                textAlign: 'center'
              }}
            >
              {data.title}
            </h1>

            <hr
              style={{
                display: 'block',
                height: '2px',
                border: 0,
                borderTop: '2px solid #e67e22',
                margin: '1em 0',
                padding: '0'
              }}
            />

            <h3>Details</h3>
            <strong>Name:</strong> {data.name}<br/>
            <strong>Year of Birth:</strong> 1987<br/>
            <strong>Current Residence:</strong> Amsterdam<br/>
            <strong>Nationality:</strong> {nationality}<br/>
            <br/>


            <p>{data.aboutMe}</p>

            <hr
              style={{
                display: 'block',
                height: '2px',
                border: 0,
                borderTop: '2px solid #e67e22',
                margin: '1em 0',
                padding: '0'
              }}
            />

            <h3>Experience</h3>
            {this.getExperience()}

            <hr
              style={{
                display: 'block',
                height: '2px',
                border: 0,
                borderTop: '2px solid #e67e22',
                margin: '1em 0',
                padding: '0'
              }}
            />

            <h3>Education</h3>
            {this.getEducation()}

            <hr
              style={{
                display: 'block',
                height: '2px',
                border: 0,
                borderTop: '2px solid #e67e22',
                margin: '1em 0',
                padding: '0'
              }}
            />

            <h3>Courses</h3>
            {this.getCourses()}

            <hr
              style={{
                display: 'block',
                height: '2px',
                border: 0,
                borderTop: '2px solid #e67e22',
                margin: '1em 0',
                padding: '0'
              }}
            />

            <h3>Languages</h3>
            {this.getLanguages()}
          </Col>
        </Row>
      </Grid>
    );
  }

  createPDF() {
    const data = this.props.user.new;
    const content = this.refs.content;
  }
}

Preview.displayName = 'Preview';

function select(state) {
  return {
    user: state.user
  };
}

module.exports = connect(select)(Preview);
