const React = require('react');
const qwest = require('qwest');
const tcombForm = require('tcomb-form');
const tcombFormTypes = require('tcomb-form-types');
const { Button, Col, Grid, Input, Row } = require('react-bootstrap');
const { bindAll, map, each } = require('lodash');

// const DateTimeInput = require('react-bootstrap-datetimepicker');
// const Select = require('react-select');

const nationalities = require('./nationalities');
const TagsComponent = require('../../../../lib/components/tcomb-form/tags');

const { Form } = tcombForm.form;

const nationalitiesObject = {};

each(nationalities, (c) => {
  nationalitiesObject[c.value] = c.label;
});

const CoursesSchema = tcombForm.struct({
  name: tcombForm.String,
  description: tcombForm.String,
  date: tcombForm.Date,
});

const DegreeSchema = tcombForm.enums({
  ASSOCIATE_DEGREE: 'Associate Degree',
  BACHELOR_DEGREE: 'Bachelor Degree',
  MASTER_DEGREE: 'Master Degree',
  ENGINEER_DEGREE: 'Engineer Degree',
  DOCTORAL: 'Doctoral',
  OTHER: 'Other'
});

const EducationSchema = tcombForm.struct({
  degree: DegreeSchema,
  fieldOfStudy: tcombForm.String,
  university: tcombForm.String,
  graduated: tcombForm.Boolean,

  // TODO: Support this type in tcomb-form-types.
  graduationYear: tcombFormTypes.Number.Decimal
});

const ExperienceSchema = tcombForm.struct({
  companyName: tcombForm.String,
  title: tcombForm.String,
  location: tcombForm.String,
  startDate: tcombForm.Date,
  endDate: tcombForm.Date,

  shortDescription: tcombForm.String,

  technologies: tcombForm.list(tcombForm.String),

  // TODO: Tag text input
  methodologies: tcombForm.list(tcombForm.String)
});

const LanguageProficiencySchema = tcombForm.enums({
  ELEMENTARY: 'Elementary',
  LIMITED_WORKING: 'Limited Working Experience',
  PROFESSIONAL_WORKING: 'Professional Working Experience',
  FULL_PROFESSIONAL: 'Full Professional',
  NATIVE: 'Native'
})

const LanguageSkillSchema = tcombForm.struct({
  name: tcombForm.String,

  proficiency: LanguageProficiencySchema
});

const EmployeeSchema = tcombForm.struct({
  title: tcombForm.String,

  name: tcombForm.String,
  surname: tcombForm.String,

  email: tcombFormTypes.String.Email,
  phonenumber: tcombForm.String,

  github: tcombForm.maybe(tcombForm.String),
  linkedin: tcombForm.maybe(tcombForm.String),

  dateOfBirth: tcombForm.Date,
  nationality: tcombForm.enums(nationalitiesObject),

  aboutMe: tcombForm.String,

  education: tcombForm.list(EducationSchema),
  courses: tcombForm.list(CoursesSchema),
  experience: tcombForm.list(ExperienceSchema),
  languages: tcombForm.list(LanguageSkillSchema)
});

class Employee extends React.Component {
  displayName = 'Employee';

  constructor(options) {
    super(options);

    bindAll(this, [
      'handleSaveButtonClick',
      'handleSubmit'
    ]);
  }

  render() {
    const options = {
      fields: {
        aboutMe: {
          type: 'textarea'
        },

        experience: {
          item: {
            fields: {
              shortDescription: {
                type: 'textarea'
              },

              technologies: {
                factory: TagsComponent
              },

              methodologies: {
                factory: TagsComponent
              }
            }
          }
        }
      }
    };

    return (
      <Grid>
        <Row>
          <Col xs={12}>
            <form onSubmit={this.handleSubmit}>
              <Form
                ref="form"
                type={EmployeeSchema}
                options={options}
                value={this.props.value}
              />
              <Button type="submit" bsStyle="primary">Save</Button>
            </form>
          </Col>
        </Row>
      </Grid>
    );
  }

  handleSubmit(event) {
    event.preventDefault();

    const value = this.refs.form.getValue();

    if (value) {
      this.props.handleSubmit(value);
    }
  }

  handleSaveButtonClick(event) {
    const button = event.target;

    const name = document.querySelector('#name').value;
    const surname = document.querySelector('#surname').value;
    const email = document.querySelector('#email').value;
    const phonenumber = document.querySelector('#phonenumber').value;
    const github = document.querySelector('#github').value;
    const linkedin = document.querySelector('#linkedin').value;
    const dateOfBirth = document.querySelector('#dateOfBirth').value;
    const countryOfBirth = document.querySelector('#countryOfBirth').value;
    const currentResidence = document.querySelector('#currentResidence').value;

    const courses = this.coursesComponent.state.entries;
    const education = this.educationComponent.state.entries;
    const experience = this.experienceComponent.state.entries;

    const data = {
      name,
      surname,
      email,
      phonenumber,
      github,
      linkedin,
      dateOfBirth,
      countryOfBirth,
      currentResidence,
      courses,
      education,
      experience
    };

    qwest
    .post('http://localhost:3000', data)
      .then((xhr, response) => {
      })
      .catch((xhr, response, error) => {
      })
  }
}

module.exports = Employee;
