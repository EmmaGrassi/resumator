import React from 'react';
import { storiesOf, action } from '@kadira/storybook';
import faker from 'faker';
import { NotFound } from './';

storiesOf('NotFound', module)
  .add('with content', () => (
    <NotFound />
  ));
