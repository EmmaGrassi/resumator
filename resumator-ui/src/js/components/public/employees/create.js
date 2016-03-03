import React from 'react';
import { connect } from 'react-redux';

import NewForm from '../../shared/form1/employee';

import create from '../../../actions/employees/create';

function mapStateToProps(state) {
  const create = state.employees.create.toJS();

  return {
    hasFailed: create.hasFailed,
    errors: create.errors,
  };
}

function mapDispatchToProps(dispatch) {
  return {
    createEmployee: (data) => dispatch(create(data)),
  };
}

class Create extends React.Component {
  render() {
    return (
      <div>
        <NewForm
          ref="employeeForm"
          type="EMPLOYEE"
          handleSubmit={this.props.createEmployee}
          hasFailed={this.props.hasFailed}
          errors={this.props.errors}
        />
      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Create);
