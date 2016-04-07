import React from 'react';
import {
  Input,
  Button,
} from 'react-bootstrap';

export default function CommentInput(props) {
  const inputProps = {
    type: 'textarea',
    placeholder: 'Write a comment...',
    label: 'Add comment',
    onChange: props.onInputChange,
    className: 'comment-input',
  };

  return (<div className="comment-input-container">
    <Input { ...inputProps } />
    <Button bsStyle="primary" onClick={props.onCommentCreate} className="pull-right">Submit</Button>
  </div>);
}
