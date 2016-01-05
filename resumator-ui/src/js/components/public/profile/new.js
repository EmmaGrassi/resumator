const React = require('react');
const { bindAll } = require('lodash');
const { connect } = require('react-redux');


const EmployeeNewForm = require('./form/employee');

const actions = require('../../../actions');

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

module.exports = connect(select)(New);
