import React from 'react';
import { connect } from 'react-redux';

import NewForm from '../../shared/form/Employee';

import create from '../../../actions/employees/create';
import createChange from '../../../actions/employees/createChange';

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
  };
}

class Register extends React.Component {
  componentWillReceiveProps(props) {
    if (!props.session.name) {
      return;
    }

    this.props.changeEmployee('name', props.session.name);
    this.props.changeEmployee('surname', props.session.surname);
    this.props.changeEmployee('email', props.session.email);
  }

  render() {
    let register;
    if (this.props.session.name) {
      register = true;
    }

    const initialObj = {
      name: this.props.session.name,
      surname: this.props.session.surname,
      email: this.props.session.email,
    };

    return (
      <div>
        <NewForm
          ref="employeeForm"
          type={this.props.params.type.toUpperCase()}
          register={register}
          values={initialObj}
          handleSubmit={this.props.createEmployee}
          handleChange={this.props.changeEmployee.bind(this)}
          hasFailed={this.props.create.hasFailed}
          errors={this.props.create.errors}
        />
      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Register);
