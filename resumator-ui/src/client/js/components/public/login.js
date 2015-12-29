const React = require('react');

const {
  Col,
  Grid,
  Row
} = require('react-bootstrap');

class Login extends React.Component {
  render() {
    return (
      <Grid>
        <Row>
          <Col xs={12}>
            Login!
          </Col>
        </Row>
      </Grid>
    );
  }
}

Login.displayName = 'Login';

module.exports = Login;
