import React from 'react';
import { storiesOf, action } from '@kadira/storybook';
import faker from 'faker';
import { Home } from './';

storiesOf('Home', module)
  .add('with content', () => (
    <Home />
  ));
