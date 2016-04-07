import React from 'react';
import { storiesOf, action } from '@kadira/storybook';
import faker from 'faker';
import Comment from './';

const commentData = {
  author: {
    name: `${faker.name.firstName()} ${faker.name.lastName()}`,
    avatar: faker.image.avatar(),
  },
  body: faker.lorem.sentence(10, 70),
  createdAt: faker.date.past(),
  replies: [
    {
      author: {
        name: `${faker.name.firstName()} ${faker.name.lastName()}`,
        avatar: faker.image.avatar(),
      },
      body: faker.lorem.sentence(10, 70),
      createdAt: faker.date.past(),
      replies: [
        {
          author: {
            name: `${faker.name.firstName()} ${faker.name.lastName()}`,
            avatar: faker.image.avatar(),
          },
          body: faker.lorem.sentence(10, 70),
          createdAt: faker.date.past(),
          replies: [],
        },
      ],
    },
    {
      author: {
        name: `${faker.name.firstName()} ${faker.name.lastName()}`,
        avatar: faker.image.avatar(),
      },
      body: faker.lorem.sentence(10, 70),
      createdAt: faker.date.past(),
      replies: [],
    },
  ],
};

storiesOf('Comment', module)
  .add('with content', () => (
    <Comment comment={commentData} />
  ));
