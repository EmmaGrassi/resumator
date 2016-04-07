import React from 'react';
import {
  Input,
} from 'react-bootstrap';
import Comment from '../Comment';

export default function CommentList(props) {
  return (<div className="comment-list-container">
    {props.comments.map((comment, i) => <Comment comment={comment} key={i} />)}
  </div>);
}
