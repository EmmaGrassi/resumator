import React from 'react';
import { connect } from 'react-redux';

import NewForm from '../../shared/form/employee';

import create from '../../../actions/employees/create';
import createChange from '../../../actions/employees/createChange';
import editCancel from '../../../actions/employees/editCancel';

function mapStateToProps(state) {
  const create = state.employees.create.toJS();

  return {
    hasFailed: create.hasFailed,
    errors: create.errors,
  };
}

function mapDispatchToProps(dispatch) {
  return {
    createEmployee: () => dispatch(create()),
    changeEmployee: (k, v) => dispatch(createChange(k, v)),
    handleCancel: () => {
      const really = confirm('Are you sure you want to cancel? This will discard all changes');
      if (really) {
        dispatch(editCancel());
      }
    },
  };
}

class Create extends React.Component {
  render() {
    return (
      <div>
        <NewForm
          ref="employeeForm"
          type="PROSPECT"
          values={{ isSaved: false }}
          handleSubmit={this.props.createEmployee}
          handleCancel={this.props.handleCancel}
          handleChange={this.props.changeEmployee.bind(this)}
          hasFailed={this.props.hasFailed}
          errors={this.props.errors}
        />
      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Create);
