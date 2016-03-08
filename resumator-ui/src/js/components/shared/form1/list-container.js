import React from 'react';
import { Button, Col, Input, Row, Alert } from 'react-bootstrap';

class ListContainer extends React.Component {
  constructor(options) {
    super(options);

    this.state = {
      forms: [
        {},
      ],
    };
  }

  componentWillReceiveProps() {
    console.log('componentWillReceiveProps');
  }

  addForm() {
    console.log('addForm');

    this.setState({
      forms: this.state.forms.concat([{}]),
    });
  }

  removeForm(index) {
    console.log('removeForm');
  }

  handleAdd(event) {
    event.preventDefault();

    this.addForm();
  }

  renderSaveButton() {
    return (
      <div className="pull-right">
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
    console.log('render');

    const {
      form,
      formProps,
    } = this.props;

    const Form = form;

    const forms = this.state.forms.map((values, iterator) => {
      formProps.values = values;
      formProps.key = iterator;

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
            {this.renderSaveButton()}
          </Col>
        </Row>
      </div>
    );
  }
}

export default ListContainer;
