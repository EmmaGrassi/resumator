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
import { pushPath } from 'redux-simple-router';

import create from '../../../actions/employees/create';

import PersonalForm from './personal';
import ExperienceForm from './experience';
import EducationForm from './education';
import CoursesForm from './courses';
import LanguagesForm from './languages';

const navItems = [
  'Personal',
  'Experience',
  'Education',
  'Courses',
  'Languages',
];

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
    navigateTo: (email, value) => dispatch(pushPath(`/employees/${email}/edit/${value.toLowerCase()}`)),
  };
}

class EmployeeForm extends React.Component {
  constructor(options) {
    super(options);

    this.state = {
      activeKey: 1,
      selectedTab: 'Personal',
    };
  }

  componentWillMount() {
    this.getActiveSection();
  }

  componentWillReceiveProps() {
    this.getActiveSection();
  }

  getEmail() {
    return this.props.profile && this.props.profile.item && this.props.profile.item.email;
  }

  getActiveSection() {
    if (this.props.section) {
      // TODO: Remove the defer
      setTimeout(() => {
        const key = navItems
          .findIndex(elem => elem.toLowerCase() === this.props.section);
        this.setState({
          activeKey: key + 1,
          selectedTab: navItems[key],
        });
      }, 0);
    } else {
      this.setState({
        activeKey: 1,
        selectedTab: 'Personal',
      });
    }
  }

  handleTabSelect(event) {
    event.preventDefault();

    const email = this.getEmail();

    if (!email) {
      return;
    }

    this.props.navigateTo(email, event.target.text);
  }

  renderNavItems() {
    const email = this.getEmail();

    return navItems.map((v, i) => {
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
      handleChange,
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
              handleChange={handleChange}
            />
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeForm);
