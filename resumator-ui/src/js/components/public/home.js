import { Button, Col, Grid, Jumbotron, Row } from 'react-bootstrap';
import React from 'react';

class Home extends React.Component {
  render() {
    return (
      <Grid>
        <Row>
          <Col xs={12}>
            <Jumbotron>
              <h1>Resumator</h1>
              <p>Needs some explanation.</p>
              <p><Button bsStyle="primary">Learn more</Button></p>
            </Jumbotron>
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default Home;
