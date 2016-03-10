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
import createChange from '../../../actions/employees/createChange';

import PersonalForm from './personal';
import ExperienceForm from './experience';
import EducationForm from './education';
import CoursesForm from './courses';
import LanguagesForm from './languages';

import ListContainer from './list-container';

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
    createChange: (k, v) => dispatch(createChange(k, v)),
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
    this.getActiveSection(this.props);
  }

  componentWillReceiveProps(props, state) {
    this.getActiveSection(props);
  }

  getEmail() {
    return this.props.profile && this.props.profile.item && this.props.profile.item.email;
  }

  getActiveSection(props) {
    const { section } = props;

    if (!section) {
      this.setState({
        activeKey: 1,
        selectedTab: 'Personal',
      });

      return;
    }

    const key = navItems
      .findIndex(elem => elem.toLowerCase() === section);

    this.setState({
      activeKey: key + 1,
      selectedTab: navItems[key],
    });
  }

  handleTabSelect(event) {
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

  renderSelectedTab() {
    const {
      errors,
      handleChange,
      handleSubmit,
      hasFailed,
      isSaving,
      register,
      values,
      handleCancel,
    } = this.props;

    let component;

    const formProps = {
      errors,
      handleChange,
      handleSubmit,
      hasFailed,
      isSaving,
      register,
      values,
      handleCancel,
    };

    switch (this.state.selectedTab) {
      case 'Experience':
        component = (
          <ListContainer
            name="experience"
            form={forms.Experience}
            formProps={formProps}
            addEntry={this.props.addEntry}
            handleSubmit={this.props.handleSubmit}
            handleCancel={this.props.handleCancel}
          />
        );
        break;

      case 'Education':
        component = (
          <ListContainer
            name="education"
            form={forms.Education}
            formProps={formProps}
            addEntry={this.props.addEntry}
            handleSubmit={this.props.handleSubmit}
            handleCancel={this.props.handleCancel}
          />
        );
        break;

      case 'Courses':
        component = (
          <ListContainer
            name="courses"
            form={forms.Courses}
            formProps={formProps}
            addEntry={this.props.addEntry}
            handleSubmit={this.props.handleSubmit}
          />
        );
        break;

      case 'Languages':
        component = (
          <ListContainer
            name="languages"
            form={forms.Languages}
            formProps={formProps}
            addEntry={this.props.addEntry}
            handleSubmit={this.props.handleSubmit}
          />
        );
        break;

      case 'Personal':
      default:
        component = (
          <forms.Personal {...formProps} />
        );

        break;
    }

    return component;
  }

  render() {
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
            {this.renderSelectedTab()}
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeForm);
