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
    updateEmployee: (id, data) => dispatch(actions.employees.update(id, data))
  }
}

class Edit extends React.Component {
  componentWillMount() {
    this.props.editEmployee(this.props.params.userId)
  }

  handleFormSubmit(id, data) {
    this.props.updateEmployee(id, data);
  }

  render() {
    const data = this.props.edit.toJS();
    const isFetching = data.isFetching;

    if (data.item) {
      data.item.dateOfBirth = new Date(data.item.dateOfBirth);

      data.item.experience = data.item.experience.map((v, i) => {
        v.endDate = new Date(v.endDate);
        v.startDate = new Date(v.startDate);

        return v;
      });
    }

    return (
      <Loader
        loaded={!isFetching}
      >
        <EmployeesEditForm
          value={data.item}
          handleSubmit={this.handleFormSubmit.bind(this, data.item.id)}
        />
      </Loader>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Edit);
