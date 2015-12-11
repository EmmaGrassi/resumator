import React from 'react';
import { connect } from 'react-redux';

import AppBar from 'material-ui/lib/app-bar';

// import LeftButton from './left-button';
// iconElementLeft={<LeftButton/>}

// iconElementRight={<RightButton/>}
// import RightButton from './right-button';

class TopBar extends React.Component {
  render() {
    return <AppBar
      title="Resumator"
    />;
  }
}

TopBar.displayName = 'TopBar';

TopBar.mapStateToProps = function mapStateToProps(state) {
  return {
    token: state.authentication.token
  };
};

export default connect(TopBar.mapStateToProps)(TopBar);
