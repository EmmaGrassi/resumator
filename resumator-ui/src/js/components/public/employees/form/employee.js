import React from 'react';
import qwest from 'qwest';
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

    const value = this.refs.form.getValue();

    if (value) {
      this.props.onSubmit(value);
    }
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
}

export default EmployeeForm;
