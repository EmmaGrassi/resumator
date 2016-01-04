const React = require('react');
const { connect } = require('react-redux');

const EmployeeEditForm = require('./form/employee');

class Edit extends React.Component {
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

Edit.displayName = 'Edit';

function select(state) {
  return {
    user: state.user
  };
}

module.exports = connect(select)(Edit);
