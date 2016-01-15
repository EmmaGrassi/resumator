import React from 'react';
import { connect } from 'react-redux';

import EmployeesNewForm from './form/employee';

import actions from '../../../actions';

function mapStateToProps(state) {
  return {
    user: state.user
  };
}


class Create extends React.Component {
  handleSubmit(data) {
    this.props.dispatch(actions.employee.new(data));

    window.location.hash = `preview`;
  }

  render() {
    const options = {
    };

    return (
      <div>
        <EmployeesNewForm
          ref="employeeForm"
          options={options}
          handleSubmit={this.handleSubmit.bind(this)}
        />
      </div>
    );
  }
}

export default connect(mapStateToProps)(Create);
