import React from 'react';
import { bindAll } from 'lodash';
import { connect } from 'react-redux';


import EmployeeNewForm from './form/employee';

import actions from '../../../actions';

class New extends React.Component {
  displayName = 'New';

  constructor(options) {
    super(options);

    bindAll(this, [
      'handleSubmit'
    ]);
  }

  render() {
    const options = {
    };

    return (
      <div>
        <EmployeeNewForm
          ref="employeeForm"
          options={options}
          handleSubmit={this.handleSubmit}
        />
      </div>
    );
  }

  handleSubmit(data) {
    this.props.dispatch(actions.employee.new(data));

    window.location.hash = `preview`;
  }
}

function select(state) {
  return {
    user: state.user
  };
}

export default connect(select)(New);
