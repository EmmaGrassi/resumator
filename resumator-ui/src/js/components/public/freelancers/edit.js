import Loader from 'react-loader';
import React from 'react';
import { connect } from 'react-redux';

import EditForm from '../../shared/form/employee';

import actions from '../../../actions';

function mapStateToProps(state) {
  return {
    edit: state.employees.edit.toJS()
  };
}

function mapDispatchToProps(dispatch) {
  return {
    editEmployee: (email) => dispatch(actions.employees.edit(email)),
    updateEmployee: (email, data) => dispatch(actions.employees.update(email, data))
  }
}

class Edit extends React.Component {
  componentWillMount() {
    this.props.editEmployee(this.props.params.userId)
  }

  handleFormSubmit(email, data) {
    this.props.updateEmployee(email, data);
  }

  render() {
    const data = this.props.edit;
    const isFetching = data.isFetching;

    if (data.item) {
      data.item.dateOfBirth = new Date(data.item.dateOfBirth);

      data.item.experience = data.item.experience.map((v, i) => {
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
          value={data.item}
          type="FREELANCER"
          handleSubmit={this.handleFormSubmit.bind(this, data.item.email)}
        />
      </Loader>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Edit);
