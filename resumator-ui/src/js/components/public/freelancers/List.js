import Loader from 'react-loader';
import React from 'react';
import ReactTable from '../../shared/table';
import _ from 'lodash';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';

import {
  Button,
  ButtonGroup,
  ButtonToolbar,
  Col,
  Glyphicon,
  Grid,
  Row,
  Table,
} from 'react-bootstrap';

import listAction from '../../../actions/employees/list';
import removeAction from '../../../actions/employees/remove';

function mapStateToProps(state) {
  return {
    list: state.employees.list.toJS(),
  };
}

function mapDispatchToProps(dispatch) {
  return {
    fetchListData: () => dispatch(listAction('FREELANCER')),
    removeListEntry: (type, email) => dispatch(removeAction(type, email)),
    navigateToEdit: (email) => dispatch(pushPath(`/freelancers/${email}/edit`, {})),
    navigateToNew: () => dispatch(pushPath('/freelancers/new', {})),
    navigateToShow: (email) => dispatch(pushPath(`/freelancers/${email}`, {})),
  };
}

class List extends React.Component {

  componentWillMount() {
    this.props.fetchListData();
  }

  handleRowButtonClick(email, event) {
    event.preventDefault();

    this.props.navigateToShow(email);
  }

  handleNewButtonClick(event) {
    event.preventDefault();

    this.props.navigateToNew();
  }

  handleEditButtonClick(email, event) {
    event.preventDefault();
    event.stopPropagation();

    this.props.navigateToEdit(email);
  }

  handleRemoveButtonClick(email, event) {
    event.preventDefault();
    event.stopPropagation();

    const answer = window.confirm('Are you sure you want to remove this employee?');

    if (!answer) {
      return;
    }

    this.props.removeListEntry('FREELANCER', email);
  }

  renderTable() {
    return (<ReactTable
      data={this.props.list.items}
      handleOpen={this.handleRowButtonClick.bind(this)}
      handleEdit={this.handleEditButtonClick.bind(this)}
      handleRemove={this.handleRemoveButtonClick.bind(this)}
      visibleKeys={['fullName', 'currentClient', 'role', 'phone']}
    />);
  }

  render() {
    const isFetching = this.props.list.isFetching;
    return (
      <div className="container">
        <Loader
          loaded={!isFetching}
        >
        {this.renderTable()}
        </Loader>
      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(List);
