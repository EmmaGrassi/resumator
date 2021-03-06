import React from 'react';
import { connect } from 'react-redux';

import NewForm from '../../../shared/form/Employee';

import create from '../../../../actions/employees/create';
import createChange from '../../../../actions/employees/createChange';
import editCancel from '../../../../actions/employees/editCancel';

function mapStateToProps(state) {
  const create = state.employees.create.toJS();
  const session = state.user.session.toJS();

  return {
    create,
    session,
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
          type={this.props.params.type.toUpperCase()}
          values={{}}
          sessionValues={this.props.session}
          handleSubmit={this.props.createEmployee}
          handleCancel={this.props.handleCancel}
          handleChange={this.props.changeEmployee.bind(this)}
          hasFailed={this.props.create.hasFailed}
          errors={this.props.create.errors}
        />
      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Create);
