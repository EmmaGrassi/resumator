import { Col, Grid, Row } from 'react-bootstrap';
import React from 'react';

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

export default Logout;
