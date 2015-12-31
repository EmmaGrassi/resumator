// Node libraries
var fs = require('fs');

var gulp = require('gulp');
var plugins = require('gulp-load-plugins')();
var gulpHelp = require('gulp-help');
gulp = gulpHelp(gulp);

var browserify = require('browserify');
var buffer = require('vinyl-buffer');
var del = require('del');
var gutil = require('gulp-util');
var liveServer = require('live-server');
var pngquant = require('imagemin-pngquant');
var runSequence = require('run-sequence');
var source = require('vinyl-source-stream');
var watchify = require('watchify');

function handleError(cb) {
  return function(error) {
    console.error(error.message && error.stack && `${error.message}\n\n${error.stack}` || error.message || error.stack || error);
    cb();
  };
}

// Browserify
function getBundle(path) {
  return browserify({
    entries: path,
    // debug: true,
    transform: []
  });
}
function runBundle() {
  return function(bundle, cb) {
    bundle
      .bundle()
      .pipe(source('app.bundle.js'))
      .pipe(gulp.dest(`build/client/js`))
      .on('end', cb);
  };
}

gulp.task('cleanDirectory', function() {
  return del([ `build/**/*` ]);
});

gulp.task('compileBabel', function(cb) {
  return gulp.src(`src/**/*.js`)
    .pipe(plugins.changed('build'))
    .pipe(plugins.sourcemaps.init())
    .pipe(plugins.babel({
      presets: [ 'es2015', 'react', 'stage-0' ]
    }))
    .on('error', handleError(cb))
    .pipe(plugins.sourcemaps.write())
    .pipe(gulp.dest('build'))
});

gulp.task('compileBrowserify', function compileBrowserify(cb) {
  var path = `build/client/js/app.js`;

  fs.exists(path, function(doesExist) {
    if (!doesExist) {
      return cb();
    }

    var bundle = getBundle(path);

    runBundle()(bundle, cb);
  });
});

gulp.task('compileCopy', function compileCopy() {
  return gulp.src(`src/**/*.!(gif|jpg|png|svg|less|js)`)
    .pipe(plugins.changed('build'))
    .pipe(gulp.dest('build'));
});

gulp.task('compileImages', function compileImages() {
  return gulp.src(`src/**/*.@(gif|jpg|png|svg)`)
    .pipe(plugins.changed('build'))
    .pipe(plugins.imagemin({
      progressive: true,
      svgoPlugins: [ { removeViewbox: false } ],
      use: [ pngquant() ]
    }))
    .pipe(gulp.dest('build'));
});

gulp.task('compileLess', function compileLess() {
  return gulp.src(`src/client/styles/app.less`)
    .pipe(plugins.changed('build'))
    .pipe(plugins.less())
    .pipe(plugins.minifyCss())
    .pipe(gulp.dest(`build/client/css`));
});

gulp.task('runLiveServer', function runLiveServer(cb) {
  liveServer.start({
    port: 8080,
    host: '0.0.0.0',
    root: 'build/client',
    open: false,
    file: 'index.html',
    wait: 1000
  });

  cb();
});

gulp.task('runServer', function runServer(cb) {
  var path = `${__dirname}/build/server/app.js`;

  fs.exists(path, function(doesExist) {
    if (!doesExist) {
      return cb();
    }

    plugins.developServer.listen({
      path: path
    });

    plugins.saneWatch(`build/server/**/*`, { debounce: 300 }, function() {
      plugins.developServer.restart();
    });

    cb();
  });
});

gulp.task('runTests', function runTests() {
  return gulp.src(`src/test/**/*.js`, { read: false })
    .pipe(plugins.mocha({
      recursive: true,
      reporter: 'spec'
    }));
});

gulp.task('watchBrowserify', function watchBrowserify(cb) {
  var path = `build/client/js/app.js`;

  fs.exists(path, function(doesExist) {
    if (!doesExist) {
      return cb();
    }

    var bundle = watchify(getBundle(path));

    bundle.on('update', function() {
      runBundle()(bundle, function() {});
    });

    bundle.on('log', gutil.log);

    runBundle()(bundle, function() {});

    cb();
  });
});

gulp.task('watchCompilers', function watchCompilers(cb) {
  plugins.saneWatch(`src/**/*`, { debounce: 300 }, function(filename, path, stat) {
    if (filename.match(/^.*\.js$/)) {
      gulp.start('compileBabel');
    }

    if (filename.match(/^.*\.less$/)) {
      gulp.start('compileLess');
    }

    if (filename.match(/^.*\.(gif|jpg|png|svg)$/)) {
      gulp.start('compileImages');
    }

    if (!filename.match(/^.*\.(gif|jpg|png|svg|less|js)$/)) {
      gulp.start('compileCopy');
    }
  });

  cb();
});

gulp.task('watchTests', function watchTests(cb) {
  gulp.watch(`src/**/*.js`, [ 'runTests' ]);

  cb();
});

gulp.task('develop', function(cb) {
  runSequence(
    'cleanDirectory',

    [
      'compileBabel',
      'compileCopy',
      'compileImages',
      'compileLess'
    ],

    'runTests',

    [
      'runServer',
      'runLiveServer',
      'watchBrowserify',
      'watchTests',
      'watchCompilers'
    ],

    cb
  );
});

gulp.task('compile', function(cb) {
  runSequence(
    'cleanDirectory',

    [
      'compileBabel',
      'compileCopy',
      'compileImages',
      'compileLess'
    ],

    'compileBrowserify',

    cb
  );
});

gulp.task('default', [ 'compile' ]);
