import Loader from 'react-loader';
import React from 'react';
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
    fetchListData: () => dispatch(listAction('PROSPECT')),
    removeListEntry: (type, email) => dispatch(removeAction(type, email)),
    navigateToEdit: (email) => dispatch(pushPath(`/prospects/${email}/edit`, {})),
    navigateToNew: () => dispatch(pushPath('/prospects/new', {})),
    navigateToShow: (email) => dispatch(pushPath(`/prospects/${email}`, {})),
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

    this.props.removeListEntry('PROSPECT', email);
  }


  render() {
    const data = this.props.list;
    const items = data.items;
    const isFetching = data.isFetching;

    let rows;

    if (items && items.length) {
      rows = items.map((v, i) => {
        const email = v.email;
        const fullName = v.fullName;
        const client = v.client;
        const title = v.title;
        const phone = v.phone;

        return (<tr
          key={i}
          onClick={this.handleRowButtonClick.bind(this, email)}
          style={{
            cursor: 'pointer',
          }}
        >
      <td
        style={{
          verticalAlign: 'middle',
        }}
      >
          {fullName}
        </td>
       <td
         style={{
           verticalAlign: 'middle',
         }}
       >
      {client}
      </td>
        <td
          style={{
            verticalAlign: 'middle',
          }}
        >
          {title}
        </td>
        <td
          style={{
            verticalAlign: 'middle',
          }}
        >
        {phone}
        </td>
          <td>
            <ButtonGroup>
              <Button onClick={this.handleEditButtonClick.bind(this, email)}>
                <Glyphicon glyph="pencil" />
              </Button>
              <Button onClick={this.handleRemoveButtonClick.bind(this, email)} bsStyle="danger">
                <Glyphicon glyph="trash" />
              </Button>
            </ButtonGroup>
          </td>
        </tr>);
      });
    } else {
      rows = (
        <tr>
          <td
            colSpan="5"
            style={{
              textAlign: 'center',
            }}
          >
            No Content
          </td>
        </tr>
      );
    }


    return (
      <Loader
        loaded={!isFetching}
      >
        <Grid>
          <Row>
            <Col xs={12}>
              <Table condensed hover responsive>
                <thead>
                  <tr>
                  <th>Name</th>
                    <th>Current Client</th>
                    <th>Title</th>
                    <th>Phone</th>
                    <th
                      style={{
                        width: '20%',
                      }}
                    >
                      Actions
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {rows}
                </tbody>
              </Table>
            </Col>
          </Row>
          <Row>
            <Col xs={12}>
              <ButtonToolbar>
                <ButtonGroup>
                  <Button bsStyle="success" onClick={this.handleNewButtonClick.bind(this)}>
                    <Glyphicon glyph="plus" /> New
                  </Button>
                </ButtonGroup>
              </ButtonToolbar>
            </Col>
          </Row>
        </Grid>
      </Loader>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(List);
