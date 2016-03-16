
import React from 'react';
import { Table, Column, Cell } from 'fixed-data-table';

import {
  Button,
  Glyphicon,
} from 'react-bootstrap';

class ReactTable extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
    };
  }

  render() {
    return (
      <Table
        rowsCount={this.props.data.length}
        rowHeight={50}
        headerHeight={50}
        width={1000}
        height={500}
      >
        <Column
          header={<Cell>Name</Cell>}
          cell={props => (
            <Cell {...props}>
              {this.props.data[props.rowIndex].fullName}
            </Cell>
          )}
          width={200}
        />
        <Column
          header={<Cell>Current client</Cell>}
          cell={props => (
            <Cell {...props}>
              {this.props.data[props.rowIndex].client}
            </Cell>
          )}
          width={200}
        />
        <Column
          header={<Cell>Role</Cell>}
          cell={props => (
            <Cell {...props}>
              {this.props.data[props.rowIndex].role}
            </Cell>
          )}
          width={200}
        />
        <Column
          header={<Cell>Phone Number</Cell>}
          cell={props => (
            <Cell {...props}>
              {this.props.data[props.rowIndex].phone}
            </Cell>
          )}
          width={200}
        />
        <Column
          header={<Cell>Actions</Cell>}
          cell={props => (
            <Cell {...props}>
              <Button onClick={this.props.handleEditButtonClick.bind(this, this.props.data[props.rowIndex].email)}>
                <Glyphicon glyph="pencil" />
              </Button>
              <Button onClick={this.props.handleRemoveButtonClick.bind(this, this.props.data[props.rowIndex].email)}
                bsStyle="danger"
              >
                <Glyphicon glyph="trash" />
              </Button>
            </Cell>
          )}
          width={200}
        />
      </Table>
    );
  }
}

export default ReactTable;

//
// _onSortChange(columnKey, sortDir) {
//   const sortIndexes = this._defaultSortIndexes.slice();
//   sortIndexes.sort((indexA, indexB) => {
//     let valueA = this._dataList[indexA][columnKey];
//     let valueB = this._dataList[indexB][columnKey];
//     let sortVal = 0;
//     if (valueA > valueB) {
//       sortVal = 1;
//     }
//     if (valueA < valueB) {
//       sortVal = -1;
//     }
//     if (sortVal !== 0 && sortDir === SortTypes.ASC) {
//       sortVal = sortVal * -1;
//     }
//
//     return sortVal;
//   });
//
//   this.setState({
//     sortedDataList: new DataListWrapper(sortIndexes, this._dataList),
//     colSortDirs: {
//       [columnKey]: sortDir,
//     },
//   });
// }
