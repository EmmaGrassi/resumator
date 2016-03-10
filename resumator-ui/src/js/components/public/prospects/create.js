import React from 'react';
import { connect } from 'react-redux';

import NewForm from '../../shared/form/employee';

import create from '../../../actions/employees/create';
import createChange from '../../../actions/employees/createChange';

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
          handleChange={this.props.changeEmployee.bind(this)}
          hasFailed={this.props.hasFailed}
          errors={this.props.errors}
        />
      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Create);
