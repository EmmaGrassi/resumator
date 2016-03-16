
import React from 'react';
import { Table, Column, Cell } from 'fixed-data-table';
import labelize from '../../helpers/labelize'

import {
  Button,
  Glyphicon,
  Input,
  Row, Col
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
    // eslint-disable-next-line
    const { sortDir, children, ...props } = this.props;
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
      dataList: this.props.data,
      colSortDirection: {},
      activeSearchKey: this.props.visibleKeys[0],
    };
  }

  componentWillReceiveProps(nextProps){
    this.setState({dataList: nextProps.data});
  }

  _onSortChange(columnKey, sortDir) {
      var sortIndexes = this.state.dataList.slice();
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
        dataList: sortIndexes,
        colSortDirection: {
          [columnKey]: sortDir,
        },
      });
    }

  changeActiveSearchKey(e){
    console.log('Search key changed', this.refs.activeSearchKey.getValue());
    this.setState({activeSearchKey: this.refs.activeSearchKey.getValue()})
  }

  handleSearchChange(e){
    if( e.target.value !== '' ){
      this.applyFilter(e.target.value);
    }else {
      this.resetFilter();
    }
  }

  applyFilter(query){
    const newList = this.props.data
      .filter(row => row[this.state.activeSearchKey].includes(query));
    this.setState({dataList: newList});
  }

  resetFilter(){
    this.setState({dataList: this.props.data});
  }

  render() {
    const {dataList, colSortDirection} = this.state;

    const tableWidth = 940;
    const celWidth = tableWidth / (this.props.visibleKeys.length + 1);

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
            {this.state.dataList[props.rowIndex][key]}
          </Cell>
        )}
        width={celWidth}
      />);
    });

    return (
      <div className="table-container">
        <Row>
          <Col xs={2}>
            <Input
              ref='activeSearchKey'
              type='select'
              placeholder='Active search key'
              onChange={this.changeActiveSearchKey.bind(this)}
              value={this.state.activeSearchKey}
            >
              {this.props.visibleKeys.map((key, i) => <option key={i} value={key}>{labelize(key)}</option>)}
            </Input>
          </Col>
          <Col xs={10}>
            <Input
              type="search"
              placeholder={`Search by ${labelize(this.state.activeSearchKey, null)}`}
              ref="keySearch"
              onChange={this.handleSearchChange.bind(this)}
            />
          </Col>
        </Row>

        <Table
          rowsCount={this.state.dataList.length}
          rowHeight={50}
          headerHeight={50}
          width={tableWidth}
          height={500}
        >
          {colums}
          <Column
            header={<Cell>Actions</Cell>}
            cell={props => (
              <Cell {...props}>
                <Button onClick={this.props.handleOpen.bind(this, this.state.dataList[props.rowIndex].email)}
                >
                  <Glyphicon glyph="eye-open" />
                </Button>
                &nbsp;
                <Button onClick={this.props.handleEdit.bind(this, this.state.dataList[props.rowIndex].email)}
                >
                  <Glyphicon glyph="pencil" />
                </Button>
                &nbsp;
                <Button onClick={this.props.handleRemove.bind(this, this.state.dataList[props.rowIndex].email)}
                  bsStyle="danger"
                >
                  <Glyphicon glyph="trash" />
                </Button>
              </Cell>
            )}
            width={celWidth}
          />
        </Table>
      </div>
    );
  }
}
export default ReactTable;
