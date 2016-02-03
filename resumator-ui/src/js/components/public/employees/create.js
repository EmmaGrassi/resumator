import React from 'react';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';

import EmployeesNewForm from './form/employee';

import actions from '../../../actions';

function mapStateToProps(state) {
  return {
    employees: state.employees
  };
}

function mapDispatchToProps(dispatch) {
  return {
    createEmployee: (data) => dispatch(actions.employees.create(data)),
    navigateToEmployeesShow: (email) => dispatch(pushPath(`/employees/${email}`, {}))
  };
}

class Create extends React.Component {
  handleFormSubmit(data) {
    this.props.createEmployee(data);
  }

  render() {
    const options = {
    };

    return (
      <div>
        <EmployeesNewForm
          ref="employeeForm"
          options={options}
          handleSubmit={this.handleFormSubmit.bind(this)}
        />
      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Create);
