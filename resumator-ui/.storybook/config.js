import { configure } from '@kadira/storybook';

function loadStories() {
  require('../stories/button');
  require('../stories/comments');
  require('../stories/commentPane');
  // require as many as stories you need.
}

configure(loadStories, module);
