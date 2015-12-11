import React from 'react';
import { connect } from 'react-redux';

import IconMenu from 'material-ui/lib/menus/icon-menu';
import MenuItem from 'material-ui/lib/menus/menu-item';
import NavigationMoreVertIcon from 'material-ui/lib/menu/';

// import LeftButton from './left-button';
// iconElementLeft={<LeftButton/>}

class RightButton extends React.Component {
  render() {
    return <IconMenu
      iconButtonElement={<NavigationMoreVertIcon/>}
    >
      <MenuItem primaryText="One" />
      <MenuItem primaryText="Two" />
      <MenuItem primaryText="Three" />
    </IconMenu>;
  }
}

RightButton.displayName = 'RightButton';

RightButton.mapStateToProps = function mapStateToProps(state) {
  return {
  };
};

export default connect(RightButton.mapStateToProps)(RightButton);
