import DateTimeInput from 'react-bootstrap-datetimepicker';
import React from 'react';
import moment from 'moment';
import serialize from 'form-serialize';
import { map, bindAll } from 'lodash';
import {
  Button,
  ButtonGroup,
  Col,
  Grid,
  Input,
  Row,
  Table
} from 'react-bootstrap';

class Education extends React.Component {
  constructor(options) {
    super(options);

    this.state = {
      mode: 'off',

      entries: [
      ],

      newEntry: {
      },

      editEntry: {
      }
    };

    bindAll(this, [
      'getListEntry',
      'handleAddButtonClick',
      'handleAddSaveButtonClick',
      'handleAddValueChange',
      'handleCancelButtonClick',
      'handleEditSaveButtonClick',
      'handleEditValueChange',
      'handleEntryEditButtonClick',
      'handleEntryRemoveButtonClick'
    ]);
  }

  getListEntry(data, i) {
    const graduated = data.graduated && 'Yes' || 'No';

    return (
      <li>
        <span className="pull-right">
          <ButtonGroup>
            <Button bsStyle="primary" onClick={this.handleEntryEditButtonClick} data-index={i}>Edit</Button>
            <Button bsStyle="danger" onClick={this.handleEntryRemoveButtonClick} data-index={i}>Remove</Button>
          </ButtonGroup>
        </span>
        <strong>Degree:</strong> <span>{data.degree}</span><br/>
        <strong>Field of Study:</strong> <span>{data.fieldOfStudy}</span><br/>
        <strong>School:</strong> <span>{data.school}</span><br/>
        <strong>Graduated:</strong> <span>{graduated}</span><br/>
        <strong>Graduation Year:</strong> <span>{data.graduationYear}</span><br/>
      </li>
    );
  }

  handleEntryEditButtonClick(event) {
    const button = event.target;
    const index = +button.dataset.index;

    this.setState((previousState, currentProps) => {
      previousState.editEntry = previousState.entries[index];

      // TODO: This is ugly to tack on here. Should move to redux.
      previousState.editEntry._index = index;

      previousState.mode = 'edit';

      return previousState;
    });
  }

  handleEntryRemoveButtonClick(event) {
    const button = event.target;
    const index = +button.dataset.index;

    this.setState((previousState, currentProps) => {
      previousState.entries.splice(index, 1);

      return previousState;
    });
  }

  getList() {
    // TODO: Use a bootstrap element here.

    let entries;

    if (this.state.entries.length) {
      entries = map(this.state.entries, this.getListEntry);
    } else {
      entries = (
        <li>
          No entries
        </li>
      );
    }

    return (
      <ul>
        {entries}
      </ul>
    );
  }

  getAddButton() {
    const bsStyle = 'primary';
    const text = 'Add';

    if (this.state.mode === 'off') {
      return <Button
        bsStyle={bsStyle}
        onClick={this.handleAddButtonClick}
      >
        {text}
      </Button>;
    }

    return '';
  }

  handleAddButtonClick() {
    let newValue;

    switch(this.state.mode) {
      case 'on':
        newValue = 'off';
        break;

      case 'off':
        newValue = 'on';
        break;

      case 'edit':
        newValue = 'off';
        break;

      default:
        newValue = 'on';
    }

    this.setState({
      mode: newValue
    });
  }

  getForm() {
    let form;
    let data;

    switch(this.state.mode) {
      // Show form with empty data.
      case 'on':
        form = (
          <div>
            <Row>
              <Col xs={12}>
                <Input onChange={this.handleAddValueChange} data-key="degree" type="text" label="Degree" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
                <Input onChange={this.handleAddValueChange} data-key="fieldOfStudy" type="text" label="Field of Study" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
                <Input onChange={this.handleAddValueChange} data-key="school" type="text" label="School" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
                <Input onChange={this.handleAddValueChange} data-key="graduated" type="checkbox" label="Graduated" wrapperClassName="col-xs-offset-2 col-xs-10" rows="5"/>
                <Input onChange={this.handleAddValueChange} data-key="graduationYear" type="date" label="Graduation Year" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
              </Col>
            </Row>
            <Row>
              <Col xsOffset={2} xs={10}>
                <ButtonGroup>
                  <Button onClick={this.handleCancelButtonClick} bsStyle="default">Cancel</Button>
                  <Button onClick={this.handleAddSaveButtonClick} bsStyle="success">Save</Button>
                </ButtonGroup>
              </Col>
            </Row>
          </div>
        );

        break;

      case 'edit':
        data = this.state.editEntry;

        form = (
          <div>
            <Row>
              <Col xs={12}>
                <Input onChange={this.handleEditValueChange} value={data.degree} data-key="degree" type="text" label="Degree" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
                <Input onChange={this.handleEditValueChange} value={data.fieldOfStudy} data-key="fieldOfStudy" type="text" label="Field of Study" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
                <Input onChange={this.handleEditValueChange} value={data.school} data-key="school" type="text" label="School" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
                <Input onChange={this.handleEditValueChange} value={data.graduated} data-key="graduated" type="checkbox" label="Graduated" wrapperClassName="col-xs-offset-2 col-xs-10" rows="5"/>
                <Input onChange={this.handleEditValueChange} value={data.graduationYear} data-key="graduationYear" type="date" label="Graduation Year" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
              </Col>
            </Row>
            <Row>
              <Col xsOffset={2} xs={10}>
                <ButtonGroup>
                  <Button onClick={this.handleCancelButtonClick} bsStyle="default">Cancel</Button>
                  <Button onClick={this.handleEditSaveButtonClick} bsStyle="success">Edit</Button>
                </ButtonGroup>
              </Col>
            </Row>
          </div>
        );

        break;

      case 'off':
      default:
        form = '';
        break;
    }

    return form;
  }

  handleCancelButtonClick() {
    this.setState({
      mode: 'off'
    });
  }

  handleAddSaveButtonClick() {
    const { newEntry } = this.state;

    this.setState((previousState, currentProps) => {
      previousState.entries.push(newEntry);

      previousState.mode = 'off';

      previousState.newEntry = {};

      return previousState;
    });
  }

  handleEditSaveButtonClick() {
    this.setState((previousState, currentProps) => {
      const { editEntry } = previousState;

      // TODO: dirrrrty _index.
      const index = editEntry._index;

      delete editEntry._index;

      // Remove the old entry from the array and insert the new one.
      previousState.entries.splice(index, 1, editEntry);

      previousState.mode = 'off';

      previousState.editEntry = {};

      return previousState;
    });
  }

  handleAddValueChange(event) {
    const targetElement = event.target;
    const targetKey = targetElement.dataset.key;
    const targetValue = targetElement.value;

    this.setState((previousState, currentProps) => {
      previousState.newEntry[targetKey] = targetValue

      return previousState;
    });
  }

  handleEditValueChange(event) {
    const targetElement = event.target;
    const targetKey = targetElement.dataset.key;
    const targetValue = targetElement.value;

    this.setState((previousState, currentProps) => {
      previousState.editEntry[targetKey] = targetValue

      return previousState;
    });
  }

  render() {
    const list = this.getList();
    const addButton = this.getAddButton();
    const form = this.getForm();

    return <div>
      <Row>
        <Col xsOffset={2} xs={10}>
          {list}
        </Col>
      </Row>

      {form}


      <Row>
        <Col xsOffset={2} xs={10}>
          {addButton}
        </Col>
      </Row>
    </div>;
  }
}

Education.displayName = 'Education';

export default Education;
