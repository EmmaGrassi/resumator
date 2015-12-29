import 'babel-polyfill';

const Application = require('./Application');

const app = window.app = new Application({
  leveldb: {
    path: './application'
  }
});

app.start();
