import { Button, Col, Grid, Jumbotron, Row } from 'react-bootstrap';
import React from 'react';
import ImportModal from './import/modal';

const headerStyles = {
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'space-around',
  alignItems: 'center',
};

class Home extends React.Component {

  constructor(props) {
    super(props);
    this.props = props;
    this.state = {
      showImportScreen: false,
    };
  }

  componentDidMount() {
  }

  onModalClose() {
    this.setState({ showImportScreen: false });
  }

  importUsingLinkedIn() {
    this.setState({ showImportScreen: true });
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
              <Button
                onClick={this.importUsingLinkedIn.bind(this)}
              >
                Import from Linkedin
              </Button>
              <p><Button bsStyle="link"><a href="http://sytac.io">Learn more</a></Button></p>
            </header>
          </Col>
        </Row>
        {this.state.showImportScreen ? <ImportModal onClose={this.onModalClose.bind(this)} /> : null }
      </Grid>
    );
  }
}

export default Home;
