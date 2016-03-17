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
  getInput(name, type, required, placeholder, autoFocus, attrs) {
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

      autoFocus,
      ...attrs,
    };

    if (hasFailed && errors[inputName]) {
      props.bsStyle = 'error';
      props.help = errors[inputName];
      props.hasFeedback = true;
    }

    return (<Input {...props} />);
  }

  getDropDown(name, options, required, autoFocus) {
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

      autoFocus,
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
}

export default FormComponent;
