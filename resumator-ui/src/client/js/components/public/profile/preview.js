const JsPDF = require('jspdf');
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
      return (
        <div
          style={{
            marginTop: '20px'
          }}
        >
          <h4>{v.companyName}</h4>
          <strong>{v.title}</strong><br/>
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
        <div>
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
        <div>
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
            <h1>{data.name} {data.surname}</h1>

            <h2>Details</h2>
            <strong>Email:</strong> {data.email}<br/>
            <strong>Phone:</strong> {data.phonenumber}<br/>
            <strong>Github:</strong> {data.github}<br/>
            <strong>Linkedin:</strong> {data.linkedin}<br/>
            <strong>Date of Birth:</strong> {moment(data.dateOfBirth).format('YYYY-MM-DD')}<br/>
            <strong>Nationality:</strong> {nationality}<br/>

            <h3>Summary</h3>
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

            <h2>Education</h2>
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

            <h2>Experience</h2>
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

            <h2>Courses</h2>
            {this.getCourses()}
          </Col>
        </Row>
      </Grid>
    );
  }

  createPDF() {
    const data = this.props.user.new;
    const content = this.refs.content;

    debugger;

    html2canvas(content, {
      onrendered: (canvas) => {
        const dataURL = canvas.toDataURL('image/png');
        const pdfDocument = new JsPDF('p', 'mm');

        pdfDocument.addImage(dataURL, 'PNG', 0, 0);

        pdfDocument.save(`${data.name} ${data.surname}.pdf`);
      }
    })
  }
}

Preview.displayName = 'Preview';

function select(state) {
  return {
    user: state.user
  };
}

module.exports = connect(select)(Preview);
