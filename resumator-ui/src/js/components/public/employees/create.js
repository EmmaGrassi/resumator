import React from 'react';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';

import EmployeesNewForm from './form/employee';

import actions from '../../../actions';

function mapStateToProps(state) {
  return {
    user: state.user
  };
}

function mapDispatchToProps(dispatch) {
  return {
    createEmployee: (data) => dispatch(actions.employees.create(data)),
    navigateToEmployeesShow: (id) => dispatch(pushPath(`/employees/${id}`, {}))
  };
}

class Create extends React.Component {
  handleFormSubmit(data) {
    this.props.createEmployee(data);

    // TODO: Get ID from returned resource from POST request.
    // this.props.navigateToEmployeesShow();
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
