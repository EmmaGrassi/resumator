import React from 'react';
import _ from 'lodash';
import jQuery from 'jquery';

import RaisedButton from 'material-ui/lib/raised-button';
import Table from 'material-ui/lib/table/table';
import TableBody from 'material-ui/lib/table/table-body';
import TableFooter from 'material-ui/lib/table/table-footer';
import TableHeader from 'material-ui/lib/table/table-header';
import TableHeaderColumn from 'material-ui/lib/table/table-header-column';
import TableRow from 'material-ui/lib/table/table-row';
import TableRowColumn from 'material-ui/lib/table/table-row-column';
import { Button, ButtonGroup, Glyphicon } from 'react-bootstrap';

import log from 'loglevel';

class CMSUsersListTableComponent extends React.Component {
  render() {
    // TODO: Comes from props?
    const tableState = {
      fixedHeader: true,
      fixedFooter: true,
      stripedRows: false,
      showRowHover: true,
      selectable: false,
      multiSelectable: false,
      enableSelectAll: false,
      deselectOnClickaway: false
    };

    const tableRows = this.props.list && _.map(this.props.list.items, (v, i) => {
      return <TableRow key={i}>
        <TableRowColumn>{v.username}</TableRowColumn>
        <TableRowColumn>{v.email}</TableRowColumn>
        <TableRowColumn>
          <RaisedButton label="Show" onClick={this.props.transitionToShowPage(v.id)}/>
          <RaisedButton label="Edit" secondary={true} onClick={this.props.transitionToEditPage(v.id)}/>
          <RaisedButton label="Destroy" primary={true} onClick={this.props.transitionToDestroyPage(v.id)}/>
        </TableRowColumn>
      </TableRow>;
    });

    return <Table
      fixedHeader={tableState.fixedHeader}
      fixedFooter={tableState.fixedFooter}
      selectable={tableState.selectable}
      multiSelectable={tableState.multiSelectable}
      >
      <TableHeader enableSelectAll={tableState.enableSelectAll}>
        <TableRow>
          <TableHeaderColumn colSpan="3" tooltip={this.domainKey} style={{textAlign: 'center'}}>
            {this.domainKey}
          </TableHeaderColumn>
        </TableRow>

        <TableRow>
          <TableHeaderColumn tooltip='The Username'>Username</TableHeaderColumn>
          <TableHeaderColumn tooltip='The Email'>Email</TableHeaderColumn>
          <TableHeaderColumn tooltip='Actions'>Actions</TableHeaderColumn>
        </TableRow>
      </TableHeader>

      <TableBody
        deselectOnClickaway={tableState.deselectOnClickaway}
        showRowHover={tableState.showRowHover}
        stripedRows={tableState.stripedRows}
        >
        {tableRows}
      </TableBody>

      <TableFooter>
        <TableRow>
          <TableRowColumn>ID</TableRowColumn>
          <TableRowColumn>Name</TableRowColumn>
          <TableRowColumn>Status</TableRowColumn>
        </TableRow>

        <TableRow>
          <TableRowColumn colSpan="3" style={{textAlign: 'center'}}>
            Super Footer
          </TableRowColumn>
        </TableRow>
      </TableFooter>
    </Table>;
  }
}

CMSUsersListTableComponent.displayName = 'CMSUsersListTableComponent';

export default CMSUsersListTableComponent;
