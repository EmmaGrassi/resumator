import Loader from 'react-loader';
import React from 'react';
import { connect } from 'react-redux';

import EditForm from '../../shared/form1/employee';

import edit from '../../../actions/employees/edit';
import update from '../../../actions/employees/update';

function mapStateToProps(state) {
  const edit = state.employees.edit.toJS();

  return {
    isFetching: edit.isFetching,
    item: edit.item,

    hasFailed: edit.hasFailed,
    errors: edit.errors,
  };
}

function mapDispatchToProps(dispatch) {
  return {
    editEmployee: (email) => dispatch(edit(email)),
    updateEmployee: (email, data) => dispatch(update(email, data)),
  };
}

class Edit extends React.Component {
  componentWillMount() {
    // TODO: Change this to email.
    this.props.editEmployee(this.props.params.userId);
  }

  render() {
    const {
      isFetching,
      hasFailed,
      item,
      errors,
    } = this.props;

    if (item) {
      item.dateOfBirth = new Date(item.dateOfBirth);

      item.experience = item.experience.map((v, i) => {
        if (v.startDate) {
          v.startDate = new Date(v.startDate);
        }

        if (v.endDate) {
          v.endDate = new Date(v.endDate);
        }

        return v;
      });
    }

    return (
      <Loader
        loaded={!isFetching}
      >
        <EditForm
          values={item}
          type="EMPLOYEE"
          handleSubmit={this.props.updateEmployee.bind(this, item.email)}
          hasFailed={hasFailed}
          errors={errors}
        />
      </Loader>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Edit);
