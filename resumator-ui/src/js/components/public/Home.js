import { Button, Col, Grid, Jumbotron, Row } from 'react-bootstrap';
import React from 'react';

const headerStyles = {
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'space-around',
  alignItems: 'center',
};

class Home extends React.Component {

  componentDidMount() {
  }

  render() {
    return (
      <Grid>
        <Row>
          <Col xs={12}>
            <header style={headerStyles}>
              <img src="http://sytac.io/images/sytac-hexagon-long.svg" />
              <h1>Sytac Resumator™</h1>
              <p>Hassle free CV management for your company.</p>
              <p><Button bsStyle="link"><a href="http://sytac.io">Learn more</a></Button></p>
            </header>
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default Home;
