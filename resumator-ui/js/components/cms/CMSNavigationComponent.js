import _ from 'lodash';
import React from 'react';
import { connect } from 'react-redux';

import log from 'loglevel';

import AppBar from 'material-ui/lib/app-bar';
import FontIcon from 'material-ui/lib/font-icon';
import IconButton from 'material-ui/lib/icon-button';
import IconMenu from 'material-ui/lib/menus/icon-menu';
import LeftNav from 'material-ui/lib/left-nav';
import ListItem from 'material-ui/lib/lists/list-item';
import Menu from 'material-ui/lib/menus/menu';
import MenuDivider from 'material-ui/lib/menus/menu-divider';
import MenuItem from 'material-ui/lib/menus/menu-item';
import NavigationMenuIcon from 'material-ui/lib/svg-icons/navigation/menu';
import NavigationMoreHorizIcon from 'material-ui/lib/svg-icons/navigation/more-horiz';
import NavigationMoreVertIcon from 'material-ui/lib/svg-icons/navigation/more-vert';
import Tab from 'material-ui/lib/tabs/tab';
import Tabs from 'material-ui/lib/tabs/tabs';

class PublicNavigationComponent extends React.Component {
  constructor() {
    super();

    _.bindAll(this, [
      '_handleAppBarLeftButtonClick',
      '_handleLeftNavListItemOnClick'
    ]);
  }

  _handleAppBarLeftButtonClick() {
    this.refs.leftNav.toggle();
  }

  _handleLeftNavListItemOnClick(menuItem) {
    debugger;
    return () => {
      debugger;
    }
  }

  getLeftNav() {
    const { history, location } = this.props;

    const menuItemData = [
      {
        route: '/cms',
        leftIcon: <FontIcon className="muidocs-icon-action-home" />,
        primaryText: 'Dashboard'
      },
      {
        route: '/cms/users',
        leftIcon: <FontIcon className="muidocs-icon-action-thumb-up" />,
        primaryText: 'Users'
      },
      {
        route: '/logout',
        leftIcon: <FontIcon className="muidocs-icon-action-lock-outline" />,
        primaryText: 'Logout'
      }
    ];

    let selectedIndex = 0;

    const currentRoute = location.pathname;

    const listItems = _.map(menuItemData, (v, i) => {
      if (v.route === currentRoute) {
        selectedIndex = i;
      }

      return <ListItem
        key={i}
        index={i}
        leftIcon={v.leftIcon}
        primaryText={v.primaryText}
        onClick={() => {
          history.pushState(null, v.route);
          this.refs.leftNav.toggle();
        }}
      />;
    });

    return <LeftNav
      ref="leftNav"
      docked={false}
      // header={}
      // menuItems={leftNavMenuItems}
      selectedIndex={selectedIndex}
    >
      {listItems}
    </LeftNav>;
  }

  getAppBarLeftButton() {
    return <IconButton onClick={this._handleAppBarLeftButtonClick}><NavigationMenuIcon /></IconButton>;
  }

  getIconMenuRightButton() {
    return <IconButton><NavigationMoreVertIcon /></IconButton>;
  }

  getIconMenu() {
    return <IconMenu iconButtonElement={this.getIconMenuRightButton()}>
      <MenuItem primaryText="Refresh" />
      <MenuItem primaryText="Send feedback" />
      <MenuItem primaryText="Settings" />
      <MenuItem primaryText="Help" />
      <MenuItem primaryText="Sign out" />
    </IconMenu>;
  }

  getAppBar() {
    return <AppBar title="CMS" iconElementLeft={this.getAppBarLeftButton()} iconElementRight={this.getIconMenu()}/>;
  }

  render() {
    return <div>
      {this.getAppBar()}
      {this.getLeftNav()}
    </div>;
  }
}

PublicNavigationComponent.displayName = 'PublicNavigationComponent';

PublicNavigationComponent.mapStateToProps = function mapStateToProps(state) {
  return {
    token: state.authentication.token
  };
};

export default connect(PublicNavigationComponent.mapStateToProps)(PublicNavigationComponent);
