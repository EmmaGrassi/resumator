import React from 'react';
import {
  Row,
  Col,
  Button,
  Input,
} from 'react-bootstrap';

import ReactQuill from 'react-quill';

import moment from 'moment';
import labelize from '../../../helpers/labelize';
import quillFormat from '../../../data/quill-formats';

class FormComponent extends React.Component {

  handleQuillChange(name, value) {
    this.props.handleChange(name, value);
  }

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

  getQuillEditor(name, required){
    return (
      <div className="quill-editor__container">
        <label className="control-label">
          {required ? labelize(name, '*') : labelize(name)}
        </label>
        <ReactQuill
          theme="snow"
          className="quill-editor"
          value={this.props.values[name]}
          onChange={this.handleQuillChange.bind(this, name)}
        >
          <ReactQuill.Toolbar key="toolbar"
                              ref="toolbar"
                              items={quillFormat} />
          <div key={`editor_${name}`}
               ref="editor"
               className="quill-contents"
               dangerouslySetInnerHTML={{__html: this.props.values[name]}}
          />
        </ReactQuill>
      </div>
    );
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
