import React from 'react';
import faker from 'faker';
import { connect } from 'react-redux';
import CommentInput from './CommentInput';
import CommentList from './CommentList';

function getFauxComments(amount, isNested = false) {
  const values = [];
  for (let i = 0; i < amount; i++) {
    values[i] = {
      author: {
        name: `${faker.name.firstName()} ${faker.name.lastName()}`,
        avatar: faker.image.avatar(),
      },
      body: faker.lorem.sentence(10, 70),
      createdAt: faker.date.past(),
      replies: !isNested ? getFauxComments(Math.floor(Math.random() * 5), true) : [],
    };
  }
  return values;
}

const fauxComments = getFauxComments(10);

function mapStateToProps(state) {
  return {
    comments: fauxComments,
  };
}

function mapDispatchToProps(dispatch) {
  return {
  };
}

function CommentPane(props) {
  return (<div className="comment-pane">
    <CommentList comments={props.comments} />
    <CommentInput />
  </div>);
}

export default connect(mapStateToProps, mapDispatchToProps)(CommentPane);
