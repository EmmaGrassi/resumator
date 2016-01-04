const React = require('react');

const {
  Col,
  Grid,
  Row
} = require('react-bootstrap');

class Logout extends React.Component {
  render() {
    return (
      <Grid>
        <Row>
          <Col xs={12}>
            Logout!
          </Col>
        </Row>
      </Grid>
    );
  }
}

Logout.displayName = 'Logout';

module.exports = Logout;
