import 'babel-polyfill';

import Application from './Application';
import log from '../lib/log';

log.setLevel('debug');

const app = window.app = new Application({
  leveldb: {
    path: './application'
  }
});

app.start();