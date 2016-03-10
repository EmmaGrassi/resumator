import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

import moment from 'moment';
import labelize from '../../../helpers/labelize';

class FormComponent extends React.Component {

  getInput(name, type, required, placeholder) {
    const { isSaving, hasFailed, errors, values } = this.props;
    const inputName = name;
    const inputLabel = required ? labelize(name, '*') : labelize(name);

    const props = {
      ref: inputName,
      type: type || 'text',
      placeholder: placeholder || inputLabel,
      label: inputLabel,

      disabled: isSaving,

      // TODO: Implement this.
      value: values[inputName],

      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (<Input {...props} />);
  }

  getDropDown(name, options, required) {
    const { isSaving, hasFailed, errors } = this.props;

    const inputName = name;
    const inputLabel = required ? labelize(name, '*') : labelize(name);

    const props = {
      ref: inputName,
      type: 'select',
      placeholder: inputLabel,
      label: inputLabel,

      disabled: isSaving,
      value: this.props.values[inputName],
      onChange: this.handleChange.bind(this, inputName),
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (
      <Input {...props} >
        {Object.keys(options).map((key, i) => <option key={i} value={key}>{options[key]}</option>)}
      </Input>
    );
  }

  handleChange(name, event) {
    console.error('Unimplemented method, go to your form Component and implement handleChange method');
  }
}

export default FormComponent;
