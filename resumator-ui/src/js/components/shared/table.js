
import React from 'react';
import { Table, Column, Cell } from 'fixed-data-table';
import labelize from '../../helpers/labelize'

import {
  Button,
  Glyphicon,
} from 'react-bootstrap';


const SortTypes = {
  ASC: 'ASC',
  DESC: 'DESC',
};

function reverseSortDirection(sortDir) {
  return sortDir === SortTypes.DESC ? SortTypes.ASC : SortTypes.DESC;
}

class SortHeaderCell extends React.Component {
  constructor(props) {
    super(props);

    this._onSortChange = this._onSortChange.bind(this);
  }

  render() {
    const {sortDir, children, ...props} = this.props;
    return (
      <Cell {...props} onClick={this._onSortChange} className="sortHeader">
        {children} {sortDir ? (sortDir === SortTypes.DESC ? '↓' : '↑') : ''}
      </Cell>
    );
  }

  _onSortChange(e) {
    e.preventDefault();

    if (this.props.onSortChange) {
      this.props.onSortChange(
        this.props.columnKey,
        this.props.sortDir ?
          reverseSortDirection(this.props.sortDir) :
          SortTypes.DESC
      );
    }
  }
}


class ReactTable extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      sortedDataList: this.props.data,
      colSortDirection: {},
    };
  }

  componentWillReceiveProps(nextProps){
    this.setState({sortedDataList: nextProps.data});
  }

  _onSortChange(columnKey, sortDir) {
      var sortIndexes = this.state.sortedDataList.slice();
      sortIndexes.sort((a, b) => {
        const valA = a[columnKey];
        const valB = b[columnKey];
        let sortVal = 0

        if (valA < valB) sortVal = -1;
        if (valA > valB) sortVal =  1;

        if (sortVal !== 0 && sortDir === SortTypes.ASC) {
          sortVal = sortVal * -1;
        }
        return sortVal
      });

      this.setState({
        sortedDataList: sortIndexes,
        colSortDirection: {
          [columnKey]: sortDir,
        },
      });
    }

  render() {
    const {sortedDataList, colSortDirection} = this.state;

    const colums = this.props.visibleKeys.map((key, i)=> {
      return (<Column
        key={`${key}${i}`}
        columnKey={key}
        header={
          <SortHeaderCell
            onSortChange={this._onSortChange.bind(this)}
            sortDir={colSortDirection[key]}>
            {labelize(key, null)}
          </SortHeaderCell>
        }
        cell={props => (
          <Cell {...props}>
            {this.state.sortedDataList[props.rowIndex][key]}
          </Cell>
        )}
        width={200}
      />);
    });

    return (
      <Table
        rowsCount={this.state.sortedDataList.length}
        rowHeight={50}
        headerHeight={50}
        width={1000}
        height={500}
      >
        {colums}
        <Column
          header={<Cell>Actions</Cell>}
          cell={props => (
            <Cell {...props}>
              <Button onClick={this.props.handleOpen.bind(this, this.state.sortedDataList[props.rowIndex].email)}
              >
                <Glyphicon glyph="eye-open" />
              </Button>
              &nbsp;
              <Button onClick={this.props.handleEdit.bind(this, this.state.sortedDataList[props.rowIndex].email)}
              >
                <Glyphicon glyph="pencil" />
              </Button>
              &nbsp;
              <Button onClick={this.props.handleRemove.bind(this, this.state.sortedDataList[props.rowIndex].email)}
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
