import React from 'react';
import { Grid, Row, Col, Jumbotron, Button } from 'react-bootstrap';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';

function mapStateToProps(state) {
  return {
  };
}

function mapDispatchToProps(dispatch) {
  return {
    navigateToHome: () => dispatch(pushPath('/')),
  };
}

class NotAuthorized extends React.Component {
  handleHomeButtonClick(event) {
    event.preventDefault();

    this.props.navigateToHome();
  }

  render() {
    return (
      <Grid>
        <Row>
          <Col xs={12}>
            <Jumbotron>
              <h1>Not Authorized</h1>
              <p>You do not have the required authorization.</p>
              <p><Button bsStyle="primary" onClick={this.handleHomeButtonClick.bind(this)}>Home</Button></p>
            </Jumbotron>
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(NotAuthorized);
