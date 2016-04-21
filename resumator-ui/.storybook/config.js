import { configure } from '@kadira/storybook';
import '../src/css/bootstrap.min.css';
import '../src/css/bootstrap-theme.min.css';
import '../src/styles/app.less';

const req = require.context('../src/js/components/', true, /story\.js$/);

function loadStories() {
  req.keys().forEach(req);
}

configure(loadStories, module);
