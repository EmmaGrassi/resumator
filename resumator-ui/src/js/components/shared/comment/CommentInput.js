import React from 'react';
import {
  Input,
  Button,
} from 'react-bootstrap';

function handleChange(e, value) {
  console.log(e, value);
}

const inputProps = {
  type: 'textarea',
  placeholder: 'Write a comment...',
  label: 'Add comment',
  onChange: handleChange,
  className: 'comment-input',
};

export default function CommentInput(props) {
  return (<div className="comment-input-container">
    <Input { ...inputProps } />
    <Button bsStyle="primary" className="pull-right">Submit</Button>
  </div>);
}
