import React from 'react';
import { storiesOf, action } from '@kadira/storybook';
import CommentInput from './';

storiesOf('CommentInput', module)
  .add('with no comments', () => (
    <CommentInput />
  ));
