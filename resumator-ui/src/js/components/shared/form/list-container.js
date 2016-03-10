import React from 'react';
import { Button, Col, Input, Row, Alert } from 'react-bootstrap';

class ListContainer extends React.Component {
  componentWillReceiveProps(props, state) {
    const currentValues = props.formProps.values[props.name];

    if (!currentValues.length) {
      props.addEntry(props.name);
    }
  }

  removeForm(index) {
    console.log('removeForm');
  }

  handleAdd(event) {
    event.preventDefault();

    this.props.addEntry(this.props.name);
  }

  renderActions() {
    return (
      <div className="pull-right">
        <Button
          bsStyle="danger"
          onClick={this.props.handleCancel}
        >
          Cancel
        </Button>
        <Button
          bsStyle="primary"
          onClick={this.props.handleSubmit}
        >
          Save
        </Button>
      </div>
    );
  }

  renderAddButton() {
    return (
      <div className="pull-left">
        <Button
          bsStyle="success"
          onClick={this.handleAdd.bind(this)}
        >
          Add
        </Button>
      </div>
    );
  }

  render() {
    const {
      name,
      form,
      formProps,
    } = this.props;

    // Upper case for creating the Element in JSX.
    const Form = form;

    const currentValues = formProps.values[name];

    const forms = currentValues.map((values, iterator) => {
      formProps.currentValues = currentValues;
      formProps.values = values;
      formProps.key = iterator;
      formProps.index = iterator;

      return <Form {...formProps} />;
    });

    return (
      <div>
        <Row>
          <Col xs={12}>
            {forms}
          </Col>
        </Row>

        <Row>
          <Col xs={12}>
            {this.renderAddButton()}
            {this.renderActions()}
          </Col>
        </Row>
      </div>
    );
  }
}

export default ListContainer;
