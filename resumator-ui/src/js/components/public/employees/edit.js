import React from 'react';
import { connect } from 'react-redux';

import EmployeesEditForm from './form/employee';

class Edit extends React.Component {
  render() {
    return (
      <div>
        <EmployeesEditForm
          value={this.props.user.item}
        />
      </div>
    );
  }
}

function select(state) {
  return {
    user: state.user
  };
}

export default connect(select)(Edit);
