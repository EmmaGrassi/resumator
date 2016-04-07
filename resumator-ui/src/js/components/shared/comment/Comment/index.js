import React from 'react';
import moment from 'moment';

export default function Comment(props) {
  const { comment } = props;
  console.log('===>>', comment);
  return (<div className={`comment ${props.className || ''}`}>
    <div className="left-hand">
      <div
        className="avatar"
        style={{
          backgroundImage: `url(${comment.author.avatar})`,
        }}
      />
    </div>
    <div className="right-hand">
      <h3>{comment.author.name}</h3>
      <div className="comment-body">
        {comment.body}
      </div>
      <div className="actions">
        <a href="#">Reply</a>
        <span className="time">
          {moment(comment.createdAt).format('DD MMM, YYYY')}
        </span>
      </div>
      <div className="replies">
        {comment.replies.map((reply, i) => <Comment className="nested" comment={reply} key={i} />)}
      </div>
    </div>
  </div>);
}
