import React from 'react';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';

import NewForm from '../shared/form/employee';

import actions from '../../../actions';

function mapStateToProps(state) {
  return {
  };
}

function mapDispatchToProps(dispatch) {
  return {
    createEmployee: (data) => dispatch(actions.employees.create(data)),
    navigateToShow: (email) => dispatch(pushPath(`/freelancers/${email}`, {}))
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
        <NewForm
          ref="employeeForm"
          options={options}
          handleSubmit={this.handleFormSubmit.bind(this)}
        />
      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Create);
