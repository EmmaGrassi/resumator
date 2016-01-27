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
    removeListEntry: (id) => dispatch(removeAction(id)),
    navigateToEmployeesEdit: (id) => dispatch(pushPath(`/employees/${id}/edit`, {})),
    navigateToEmployeesNew: () => dispatch(pushPath(`/employees/new`, {})),
    navigateToEmployeesShow: (id) => dispatch(pushPath(`/employees/${id}`, {}))
  };
}

class List extends React.Component {
  handleRowButtonClick(id, event) {
    event.preventDefault();

    this.props.navigateToEmployeesShow(id);
  }

  handleNewButtonClick(event) {
    event.preventDefault();

    this.props.navigateToEmployeesNew();
  }

  handleEditButtonClick(id, event) {
    event.preventDefault();
    event.stopPropagation();

    this.props.navigateToEmployeesEdit(id);
  }

  handleRemoveButtonClick(id, event) {
    event.preventDefault();
    event.stopPropagation();

    const answer = window.confirm('Are you sure you want to remove this employee?');

    if (!answer) {
      return;
    }

    this.props.removeListEntry(id);
  }

  componentWillMount() {
    this.props.fetchListData();
  }

  render() {
    const data = this.props.employees.list.toJS();
    const items = data.items;
    const isFetching = data.isFetching;

    let rows;

    if (items && items.length) {
      rows = items.map((v, i) => {
        const id = v.id;
        const name = v.name;
        const surname = v.surname;

        return <tr
          key={i}
          onClick={this.handleRowButtonClick.bind(this, id)}
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
              <Button onClick={this.handleEditButtonClick.bind(this, id)}><Glyphicon glyph="pencil"/> Edit</Button>
              <Button onClick={this.handleRemoveButtonClick.bind(this, id)} bsStyle="danger"><Glyphicon glyph="trash" /> Remove</Button>
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
