import Loader from 'react-loader';
import React from 'react';
import { connect } from 'react-redux';

import EmployeesEditForm from './form/employee';

import actions from '../../../actions';

function mapStateToProps(state) {
  return {
    edit: state.employees.get('edit')
  };
}

function mapDispatchToProps(dispatch) {
  return {
    editEmployee: (id) => dispatch(actions.employees.edit(id)),
    updateEmployee: (data) => dispatch(actions.employees.update(data))
  }
}

class Edit extends React.Component {
  componentWillMount() {
    this.props.editEmployee(this.props.params.userId)
  }

  handleFormSubmit(data) {
    this.props.updateEmployee(data);
  }

  render() {
    let item = this.props.edit.get('item');
    const isFetching = this.props.edit.get('isFetching');

    if (item) {
      item = item.toJS();

      // Create date objects out of date values because the form needs them like
      // this.
      item.dateOfBirth = new Date(item.dateOfBirth);

      item.courses = item.courses.map((v, i) => {
        v.date = new Date(v.date);

        return v;
      });

      item.experience = item.experience.map((v, i) => {
        v.endDate = new Date(v.endDate);
        v.startDate = new Date(v.startDate);

        return v;
      });
    } else {
      item = null;
    }

    return (
      <Loader
        loaded={!isFetching}
      >
        <EmployeesEditForm
          value={item}
          onSubmit={this.handleFormSubmit.bind(this)}
        />
      </Loader>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Edit);
