import React from 'react';
import { connect } from 'react-redux';

import EmployeeEditForm from './form/employee';

class Edit extends React.Component {
  displayName = 'Edit';

  render() {
    return (
      <div>
        <EmployeeEditForm
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
