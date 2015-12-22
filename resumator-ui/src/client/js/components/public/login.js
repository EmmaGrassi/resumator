const React = require('react');

import {
  Col,
  Grid,
  Row
} from 'react-bootstrap';

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
