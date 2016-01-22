import React from 'react';
import moment from 'moment';
import tcombForm from 'tcomb-form';
import tcombFormTypes from 'tcomb-form-types';
import { Button, Col, Grid, Input, Row } from 'react-bootstrap';
import { bindAll, map, each } from 'lodash';

// import DateTimeInput from 'react-bootstrap-datetimepicker';
// import Select from 'react-select';

import nationalities from './nationalities';
import TagsComponent from '../../../../lib/components/tcomb-form/tags';

const { Form } = tcombForm.form;

const nationalitiesObject = {};

each(nationalities, (c) => {
  nationalitiesObject[c.value] = c.label;
});

const CoursesSchema = tcombForm.struct({
  name: tcombForm.String,
  description: tcombForm.String,
  year: tcombForm.Number
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
  school: tcombForm.String,
  city: tcombForm.String,
  country: tcombForm.String,
  startYear: tcombForm.Number,
  endYear: tcombForm.Number
});

const ExperienceSchema = tcombForm.struct({
  companyName: tcombForm.String,
  title: tcombForm.String,
  city: tcombForm.String,
  country: tcombForm.String,
  startDate: tcombForm.Date,
  endDate: tcombForm.maybe(tcombForm.Date),
  shortDescription: tcombForm.String,
  technologies: tcombForm.list(tcombForm.String),
  methodologies: tcombForm.list(tcombForm.String)
});

const LanguageProficiencySchema = tcombForm.enums({
  ELEMENTARY: 'Elementary',
  LIMITED_WORKING: 'Limited Working Experience',
  PROFESSIONAL_WORKING: 'Professional Working Experience',
  FULL_PROFESSIONAL: 'Full Professional',
  NATIVE: 'Native'
});

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

  currentResidence: tcombForm.String,

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

class EmployeeForm extends React.Component {
  handleSubmit(event) {
    event.preventDefault();

    let value = this.refs.form.getValue();

    if (value) {
      value = JSON.stringify(value);
      value = JSON.parse(value);

      value.dateOfBirth = moment(value.dateOfBirth).format('YYYY-MM-DD');

      value.experience = map(value.experience, (v) => {
        v.startDate = moment(v.startDate).format('YYYY-MM-DD');
        if(v.endDate) {
          v.endDate = moment(v.endDate).format('YYYY-MM-DD');
        }

        return v;
      });

      this.props.handleSubmit(value);
    }
  }

  render() {
    const options = {
      fields: {
        aboutMe: {
          type: 'textarea'
        },

        courses: {
          item: {
            fields: {
              description: {
                type: 'textarea'
              }
            }
          }
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
            <form onSubmit={this.handleSubmit.bind(this)}>
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
}

export default EmployeeForm;
