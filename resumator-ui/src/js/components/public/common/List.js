import Loader from 'react-loader';
import React from 'react';
import ReactTable from '../../shared/ReactTable';
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
    fetchListData: (type) => dispatch(listAction(type)),
    removeListEntry: (type, email) => dispatch(removeAction(type, email)),
    navigateToEdit: (email) => dispatch(pushPath(`/employees/${email}/edit`, {})),
    navigateToNew: () => dispatch(pushPath('/employees/new', {})),
    navigateToShow: (email) => dispatch(pushPath(`/employees/${email}`, {})),
  };
}

class List extends React.Component {
  constructor(props) {
    super(props);

    this.type = props.params.type;
  }

  getType(props) {
    let type = (props || this.props).params.type.toUpperCase();

    type = type.substr(0, type.length - 1);

    return type;
  }

  componentWillMount() {
    this.props.fetchListData(this.getType());
  }

  componentWillReceiveProps(props) {
    // Only make a request when the type has actually changed, to prevent an
    // infinite loop.
    if (props.params.type !== this.type) {
      this.type = props.params.type;

      this.props.fetchListData(this.getType(props));
    }
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

    this.props.removeListEntry(this.getType(), email);
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
