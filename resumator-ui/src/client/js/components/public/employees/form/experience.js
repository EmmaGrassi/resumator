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

class Experience extends React.Component {
  constructor(options) {
    super(options);

    this.state = {
      mode: 'on',

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
    return (
      <li>
        <span className="pull-right">
          <ButtonGroup>
            <Button bsStyle="primary" onClick={this.handleEntryEditButtonClick} data-index={i}>Edit</Button>
            <Button bsStyle="danger" onClick={this.handleEntryRemoveButtonClick} data-index={i}>Remove</Button>
          </ButtonGroup>
        </span>
        <strong>Company Name:</strong> <span>{data.companyName}</span><br/>
        <strong>Job Title:</strong> <span>{data.title}</span><br/>
        <strong>Location:</strong> <span>{data.location}</span><br/>
        <strong>Start Date:</strong> <span>{data.startDate}</span><br/>
        <strong>End Date:</strong> <span>{data.endDate}</span><br/>
        <strong>Short Description:</strong> <span>{data.shortDescription}</span><br/>
        <strong>Technologies:</strong> <span>{data.technologies}</span><br/>
        <strong>Methodologies:</strong> <span>{data.methodologies}</span><br/>
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
                <Input onChange={this.handleAddValueChange} data-key="companyName" type="text" label="Company Name" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <pre>CHANGE INTO MULTIPLE DROPDOWNS LISTING ALL JOB TITLES</pre>
                <Input onChange={this.handleAddValueChange} data-key="title" type="text" label="Job Title" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <Input onChange={this.handleAddValueChange} data-key="location" type="text" label="Location" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <Input onChange={this.handleAddValueChange} data-key="startDate" type="date" label="Start Date" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <Input onChange={this.handleAddValueChange} data-key="endDate" type="date" label="End Date" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <Input onChange={this.handleAddValueChange} data-key="shortDescription" type="textarea" label="Short Description" labelClassName="col-xs-2" wrapperClassName="col-xs-10" rows="5" />
                <Input onChange={this.handleAddValueChange} data-key="technologies" type="text" label="Technologies" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <Input onChange={this.handleAddValueChange} data-key="methodologies" type="text" label="Methodologies" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
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
                <Input onChange={this.handleEditValueChange} value={data.companyName} data-key="companyName" type="text" label="Company Name" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <Input onChange={this.handleEditValueChange} value={data.title} data-key="title" type="text" label="Job Title" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <Input onChange={this.handleEditValueChange} value={data.location} data-key="location" type="text" label="Location" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <Input onChange={this.handleEditValueChange} value={data.startDate} data-key="startDate" type="date" label="Start Date" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <Input onChange={this.handleEditValueChange} value={data.endDate} data-key="endDate" type="date" label="End Date" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <Input onChange={this.handleEditValueChange} value={data.shortDescription} data-key="shortDescription" type="textarea" label="Short Description" labelClassName="col-xs-2" wrapperClassName="col-xs-10" rows="5" />
                <Input onChange={this.handleEditValueChange} value={data.technologies} data-key="technologies" type="text" label="Technologies" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
                <Input onChange={this.handleEditValueChange} value={data.methodologies} data-key="methodologies" type="text" label="Methodologies" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
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

    /*
    return <div>
      <Row>
        <Col xs={10}>
          <DateTimeInput
            dateTime={moment().format('YY/MM/DD')}
            format='YY/MM/DD'
            inputFormat='YY/MM/DD'
            label="Date of birth"
            mode="date"
            showToday={true}
          />
        </Col>
      </Row>

      <Col xs={2}>
      </Col>
      <Col xs={10}>
        <DateTimeInput
          dateTime={moment().format('YY/MM/DD')}
          format='YY/MM/DD'
          inputFormat='YY/MM/DD'
          label="Date of birth"
          mode="date"
          showToday={true}
        />
      </Col>
    </div>;
    */
  }
}

Experience.displayName = 'Experience';

export default Experience;
