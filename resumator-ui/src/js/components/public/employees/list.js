import Loader from 'react-loader';
import React from 'react';
import { Button, ButtonGroup, ButtonToolbar, Col, Glyphicon, Grid, Row, Table } from 'react-bootstrap';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';

import listAction from '../../../actions/employees/list';
import removeAction from '../../../actions/employees/remove';

function mapStateToProps(state) {
  return {
    employees: state.employees
  };
}

function mapDispatchToProps(dispatch) {
  return {
    fetchListData: () => dispatch(listAction()),
    removeListEntry: (email) => dispatch(removeAction(email)),
    navigateToEmployeesEdit: (email) => dispatch(pushPath(`/employees/${email}/edit`, {})),
    navigateToEmployeesNew: () => dispatch(pushPath(`/employees/new`, {})),
    navigateToEmployeesShow: (email) => dispatch(pushPath(`/employees/${email}`, {}))
  };
}

class List extends React.Component {
  handleRowButtonClick(email, event) {
    event.preventDefault();

    this.props.navigateToEmployeesShow(email);
  }

  handleNewButtonClick(event) {
    event.preventDefault();

    this.props.navigateToEmployeesNew();
  }

  handleEditButtonClick(email, event) {
    event.preventDefault();
    event.stopPropagation();

    this.props.navigateToEmployeesEdit(email);
  }

  handleRemoveButtonClick(email, event) {
    event.preventDefault();
    event.stopPropagation();

    const answer = window.confirm('Are you sure you want to remove this employee?');

    if (!answer) {
      return;
    }

    this.props.removeListEntry(email);
  }

  componentWillMount() {
    this.props.fetchListData();
  }

  render() {
    const data = this.props.employees.list;
    const items = data.items;
    const isFetching = data.isFetching;

    let rows;

    if (items && items.length) {
      rows = items.map((v, i) => {
        const email = v.email;
        const name = v.name;
        const surname = v.surname;

        return <tr
          key={i}
          onClick={this.handleRowButtonClick.bind(this, email)}
          style={{
          cursor: 'pointer'
        }}
        >
          <td
            style={{
            verticalAlign: 'middle'
          }}
          >
            {name}
          </td>
          <td
            style={{
            verticalAlign: 'middle'
          }}
          >
            {surname}
          </td>
          <td>
            <ButtonGroup>
              <Button onClick={this.handleEditButtonClick.bind(this, email)}><Glyphicon glyph="pencil"/> Edit</Button>
              <Button onClick={this.handleRemoveButtonClick.bind(this, email)} bsStyle="danger"><Glyphicon glyph="trash" /> Remove</Button>
            </ButtonGroup>
          </td>
        </tr>;
      });
    } else {
      rows = (
        <tr>
          <td
            colSpan="3"
            style={{
              textAlign: 'center'
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
              <Table striped condensed hover responsive>
                <thead>
                  <tr>
                    <th>
                      Name
                    </th>
                    <th>Surname</th>
                    <th
                      style={{
                        width: '20%'
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
                  <Button bsStyle="success" onClick={this.handleNewButtonClick.bind(this)}><Glyphicon glyph="plus"/> New</Button>
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
