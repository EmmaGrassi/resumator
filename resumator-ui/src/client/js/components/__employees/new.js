const React = require('react');

const EmployeeForm = require('./form/employee');

class New extends React.Component {
  render() {
    return (
      <EmployeeForm/>
    );
  }
}

New.displayName = 'New';

module.exports = New;
