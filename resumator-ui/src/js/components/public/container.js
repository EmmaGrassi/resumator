import React from 'react';
import { connect } from 'react-redux';
import TopBar from './navigation/top-bar';
import { Alert } from 'react-bootstrap';

const styles = {
  alerts: {
    maxWidth: '22em',
    width: '100%',
    position: 'fixed',
    bottom: '1em',
    right: '1em',
  },
};

function mapStateToProps(state) {
  const alertState = state.alerts.toJS();
  return {
    showAlerts: alertState.showAlerts,
    alerts: alertState.alerts,
  };
}

function mapDispatchToProps(dispatch) {
  return {
  };
}

class Container extends React.Component {
  componentDidMount() {
  }

  renderAlerts() {
    return this.props.alerts
      .map((alert, i) => <Alert bsStyle={alert.level} key={i}>{alert.message}</Alert>);
  }

  render() {
    return (
      <div>
        <TopBar
          history={this.props.history}
          location={this.props.location}
          params={this.props.params}
          route={this.props.route}
          routeParams={this.props.routeParams}
          routes={this.props.routes}
        />
        {this.props.children}
        <div style={styles.alerts}>{this.renderAlerts()}</div>
      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Container);
