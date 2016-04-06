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

import PersonalForm from './Personal';
import ExperienceForm from './Experience';
import EducationForm from './Education';
import CoursesForm from './Courses';
import LanguagesForm from './Languages';

import ListContainer from './ListContainer';

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

function findErrorTab(props) {
  const personalTabKeys = ['role', 'surname', 'name', 'email', 'phonenumber', 'dateOfBirth', 'cityOfResidence', 'countryOfResidence'];
  return props.errors ? Object.keys(props.errors).map(key => personalTabKeys.includes(key) ? 'personal' : key.toLowerCase()) : [];
}

function mapStateToProps(state) {
  return {
    profile: state.user.profile.toJS(),
    session: state.user.session.toJS(),
  };
}

function mapDispatchToProps(dispatch) {
  return {
    navigateTo: (email, type, route) => dispatch(pushPath(`/${type}/${email}/edit/${route.toLowerCase()}`)),
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
    const activeKey = this.getActiveTabKey(this.props);
    this.setState({
      activeKey: activeKey + 1,
      selectedTab: navItems[activeKey],
    });
  }

  componentWillReceiveProps(props, state) {
    const activeKey = this.getActiveTabKey(props);
    this.setState({
      activeKey: activeKey + 1,
      selectedTab: navItems[activeKey],
    });
  }

  getActiveTabKey({ section = 'personal' }) {
    return navItems.findIndex(elem => elem.toLowerCase() === section);
  }

  getEmail() {
    return this.props && this.props.userId;
  }

  getType() {
    return this.props.values && this.props.values.type;
  }

  isSaved() {
    return !(typeof this.props.userId === 'undefined');
  }

  handleTabSelect(event) {
    const email = this.getEmail();
    const type = this.getType();

    if (!email || !type) {
      return;
    }

    this.props.navigateTo(email, `${type.toLowerCase()}s`, event.target.text);
  }


  renderNavItems() {
    const errors = findErrorTab(this.props);
    return navItems.map((v, i) => {
      const hasErrors = errors.includes(v.toLowerCase());
      if (i === 0) {
        return (<NavItem
          key={i}
          eventKey={i + 1}
          ref={`${v}Tab`}
          className={hasErrors ? 'withError' : null}
        >{v}
        </NavItem>);
      }

      return (
        <NavItem
          key={i} eventKey={i + 1}
          ref={`${v}Tab`}
          disabled={!this.isSaved()}
          className={hasErrors ? 'withError' : null}
        >
          {v}
        </NavItem>);
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
      profile,
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
      profile,
      handleCancel,
      selectedTab: this.state.selectedTab,
    };

    return (this.state.selectedTab === 'Personal') ?
      (<forms.Personal {...formProps} />) :
      (<ListContainer
        name={this.state.selectedTab.toLowerCase()}
        form={forms[this.state.selectedTab]}
        formProps={formProps}
        addEntry={this.props.addEntry}
        removeEntry={this.props.removeEntry}
        handleSubmit={this.props.handleSubmit}
        handleCancel={this.props.handleCancel}
      />);
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
