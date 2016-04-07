import React from 'react';
import { storiesOf, action } from '@kadira/storybook';
import { CommentPane } from './';
import faker from 'faker';

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

storiesOf('CommentPane', module)
  .add('with no comments', () => (
    <CommentPane
      comments={[]}
      onInputChange={action('comment input change')}
      onCommentCreate={action('comment created')}
    />
  ))
  .add('with comments', () => (
    <CommentPane
      onInputChange={action('comment input change')}
      onCommentCreate={action('comment created')}
      comments={fauxComments}
    />
  ));
