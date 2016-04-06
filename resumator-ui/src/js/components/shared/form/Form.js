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
import stripStyles from '../../../helpers/stripStyles';
import quillFormat from '../../../data/quill-formats';

function showError(props, inputName){
  const { hasFailed, errors } = props;
  const currentSection = props.selectedTab.toLowerCase();
  return hasFailed && errors[inputName] || hasFailed && errors[currentSection] &&
  errors[currentSection][props.index] && errors[currentSection][props.index][inputName];
}

function getErrorMsg(props, inputName){
  const { errors } = props;
  const currentSection = props.selectedTab.toLowerCase();
  return errors[currentSection] &&
  errors[currentSection][props.index] &&
  errors[currentSection][props.index][inputName] ?
  errors[currentSection][props.index][inputName] : errors[inputName]
}

class FormComponent extends React.Component {

  handleQuillChange(name, value) {
    this.props.handleChange(name, stripStyles(value));
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
      value: values[inputName],

      onChange: this.handleChange.bind(this, inputName),

      autoFocus,
      ...attrs,
    };


    if (showError(this.props, inputName)) {
      const helpMsg = getErrorMsg(this.props, inputName);
      props.bsStyle = 'error';
      props.help = helpMsg;
      props.hasFeedback = true;
    }


    return (<Input {...props} />);
  }

  getQuillEditor(name, required){
    const quillProps = {
      theme: 'snow',
      className: 'quill-editor',
      onChange: this.handleQuillChange.bind(this, name),
    };

    if (this.props.values[name]) quillProps.value = this.props.values[name];

    return (
      <div className="quill-editor__container">
        <label className="control-label">
          {required ? labelize(name, '*') : labelize(name)}
        </label>
        <ReactQuill {...quillProps }>
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

    if (showError(this.props, inputName)) {
      const help = getErrorMsg(this.props, inputName);
      props.bsStyle = 'error';
      props.help = help;
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
