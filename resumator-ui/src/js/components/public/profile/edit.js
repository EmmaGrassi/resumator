const React = require('react');
const { connect } = require('react-redux');

const EmployeeEditForm = require('./form/employee');

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

module.exports = connect(select)(Edit);
