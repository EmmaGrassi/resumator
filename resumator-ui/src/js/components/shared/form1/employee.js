import React from 'react';
import {
  Button,
  Col,
  Grid,
  Input,
  Nav,
  NavItem,
  Row,
} from 'react-bootstrap';
import { connect } from 'react-redux';

import create from '../../../actions/employees/create';

import PersonalForm from './personal';
import ExperienceForm from './experience';
import EducationForm from './education';
import CoursesForm from './courses';
import LanguagesForm from './languages';

const forms = {
  Personal: PersonalForm,
  Experience: ExperienceForm,
  Education: EducationForm,
  Courses: CoursesForm,
  Languages: LanguagesForm,
};

function mapStateToProps(state) {
  return {
    profile: state.user.profile.toJS(),
  };
}

function mapDispatchToProps(dispatch) {
  return {
  };
}

class EmployeeForm extends React.Component {
  constructor(options) {
    super(options);

    this.state = {
      items: [
        'Personal',
        'Experience',
        'Education',
        'Courses',
        'Languages',
      ],

      activeKey: 1,
      selectedTab: 'Personal',
    };
  }

  getEmail() {
    return this.props.profile && this.props.profile.item && this.props.profile.item.email;
  }

  handleTabSelect(event) {
    event.preventDefault();

    const email = this.getEmail();

    if (!email) {
      return;
    }

    // TODO: Kill tom for this.
    const target = event.target;
    const value = target.text;
    const tabComponent = this.refs[`${value}Tab`];
    const newActiveKey = tabComponent.props.eventKey;

    this.setState({
      activeKey: newActiveKey,
      selectedTab: value,
    });
  }

  renderNavItems() {
    const email = this.getEmail();

    return this.state.items.map((v, i) => {
      if (i === 0) {
        return <NavItem key={i} eventKey={i + 1} ref={`${v}Tab`}>{v}</NavItem>;
      }

      return <NavItem key={i} eventKey={i + 1} ref={`${v}Tab`} disabled={!email}>{v}</NavItem>;
    });
  }

  render() {
    const CurrentFormComponent = forms[this.state.selectedTab];

    const {
      isSaving,
      hasFailed,
      errors,
      values,
      handleSubmit,
    } = this.props;

    return (
      <Grid>
        <Row>
          <Col xs={12}>
            <Nav
              ref="tabs"
              bsStyle="tabs"
              onClick={this.handleTabSelect.bind(this)}
              justified
              activeKey={this.state.activeKey}
            >
              {this.renderNavItems()}
            </Nav>
          </Col>
        </Row>
        <Row
          style={{
            marginTop: '20px',
          }}
        >
          <Col xs={12}>
            <CurrentFormComponent
              isSaving={isSaving}
              hasFailed={hasFailed}
              values={values}
              errors={errors}
              handleSubmit={handleSubmit}
            />
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeForm);
