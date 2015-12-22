// Enable ES6 code from here.
require('babel-register')({
  // TODO: Take these options from a shared configuration JSON file somewhere.
  presets: [ 'es2015', 'react', 'stage-0' ]
});

var gulp = require('gulp');
var idBuilder = require('id-builder');

idBuilder(gulp, {
  tasks: {
    runServer: {
      enabled: false
    },

    runTests: {
      enabled: false
    },

    watchTests: {
      enabled: false
    }
  }
});
