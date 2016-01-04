const _ = require('lodash');
const React = require('react');

const {
  Button,
  ButtonInput,
  Col,
  Grid,
  Input,
  Jumbotron,
  Row,
  Table
} = require('react-bootstrap');

class List extends React.Component {
  constructor(options) {
    super(options);

    _.bindAll(this, [
      'handleNewButtonClick'
    ]);
  }

  handleNewButtonClick(event) {
    event.preventDefault();

    // TODO: Probably use React Router for this.
    window.location.hash = '#/employees/new';
  }

  render() {
    return (
      <Grid>
        <Row>
          <Col xs={6}>
            <ButtonInput type="submit" value="New" bsStyle="success" onClick={this.handleNewButtonClick}/>
          </Col>

          {/* TODO: Need to use pull-right here, not columns, to align. */}
          <Col xs={4}>
            <Input type="text" ref="input" placeholder="Search..."/>
          </Col>
          <Col xs={2}>
            <ButtonInput type="submit" value="Search" bsStyle="primary"/>
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            <Table striped bordered condensed hover>
              <thead>
                <tr>
                  <th>#</th>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Username</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td>Mark</td>
                  <td>Otto</td>
                  <td>@mdo</td>
                </tr>
                <tr>
                  <td>2</td>
                  <td>Jacob</td>
                  <td>Thornton</td>
                  <td>@fat</td>
                </tr>
                <tr>
                  <td>3</td>
                  <td colSpan="2">Larry the Bird</td>
                  <td>@twitter</td>
                </tr>
              </tbody>
            </Table>
          </Col>
        </Row>
      </Grid>
    );
  }
}

List.displayName = 'List';

module.exports = List;
