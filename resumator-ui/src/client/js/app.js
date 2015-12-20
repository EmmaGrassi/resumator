import 'babel-polyfill';

const Application = require('./Application');
const log = require('../lib/log');

log.setLevel('debug');

const app = window.app = new Application({
  leveldb: {
    path: './application'
  }
});

app.start();
