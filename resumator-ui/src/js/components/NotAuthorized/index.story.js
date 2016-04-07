import React from 'react';
import { storiesOf, action } from '@kadira/storybook';
import faker from 'faker';
import { NotAuthorized } from './';

storiesOf('NotAuthorized', module)
  .add('with content', () => (
    <NotAuthorized />
  ));
