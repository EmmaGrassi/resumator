import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

import labelize from '../../../helpers/labelize';

class FormComponent extends React.Component {

  getInput(name, type) {
    const { isSaving, hasFailed, errors, values } = this.props;
    const inputName = name;
    const inputLabel = labelize(name);

    const props = {
      ref: inputName,
      type: type || 'text',
      placeholder: inputLabel,
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

  handleChange(name, event) {
    console.error('Unimpleneted method, go to your form Component and implenent handleChange method');
  }
}

export default FormComponent;
