import Loader from 'react-loader';
import React from 'react';
import moment from 'moment';
import { connect } from 'react-redux';

import EditForm from '../../shared/form/employee';

import edit from '../../../actions/employees/edit';
import update from '../../../actions/employees/update';
import editChange from '../../../actions/employees/editChange';
import addEntry from '../../../actions/employees/addEntry';

function mapStateToProps(state) {
  const edit = state.employees.edit.toJS();
  const alertState = state.alerts.toJS();

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
    updateEmployee: (email) => dispatch(update(email)),
    changeEmployee: (k, v) => {
      dispatch(editChange(k, v));
    },

    addEntry: (name) => {
      dispatch(addEntry(name));
    }
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
      params,
      } = this.props;

    if (item) {
      item.dateOfBirth = moment(item.dateOfBirth).format('YYYY-MM-DD');
      item.isSaved = true;
      item.experience = item.experience.map((v, i) => {
        if (v.startDate) {
          v.startDate = moment(v.startDate).format('YYYY-MM-DD');
        }

        if (v.endDate) {
          v.endDate = moment(v.endDate).format('YYYY-MM-DD');
        }

        return v;
      });
    }

    return (
      <Loader
        loaded={!isFetching}
      >
        <EditForm
          type="FREELANCER"
          values={item}
          addEntry={this.props.addEntry}
          section={params.section}
          handleSubmit={this.props.updateEmployee.bind(this, item.email)}
          handleChange={this.props.changeEmployee.bind(this)}
          hasFailed={hasFailed}
          errors={errors}
        />
      </Loader>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Edit);
