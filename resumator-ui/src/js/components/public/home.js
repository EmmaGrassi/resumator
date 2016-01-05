const React = require('react');

const {
  Button,
  Col,
  Grid,
  Jumbotron,
  Row
} = require('react-bootstrap');

class Home extends React.Component {
  displayName = 'Home';

  render() {
    return (
      <Grid>
        <Row>
          <Col xs={12}>
            <Jumbotron>
              <h1>Resumatorator</h1>
              <p>Needs some explanation.</p>
              <p><Button bsStyle="primary">Learn more</Button></p>
            </Jumbotron>
          </Col>
        </Row>
      </Grid>
    );
  }
}

module.exports = Home;
