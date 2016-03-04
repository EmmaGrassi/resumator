import { Button, Col, Grid, Jumbotron, Row } from 'react-bootstrap';
import React from 'react';


const headerStyles = {
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'space-around',
  alignItems: 'center'
};

class Home extends React.Component {

  render() {
    return (
      <Grid>
        <Row>
          <Col xs={12}>
            <header style={headerStyles}>
              <img src="http://sytac.io/images/sytac-hexagon-long.svg" />
              <h1>Resumator</h1>
              <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
              <p><Button bsStyle="primary">Learn more</Button></p>
            </header>
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default Home;
