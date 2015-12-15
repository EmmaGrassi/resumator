import React from 'react';

import EmployeeForm from './form/employee';

class New extends React.Component {
  render() {
    return (
      <EmployeeForm/>
    );
  }
}

New.displayName = 'New';

export default New;
