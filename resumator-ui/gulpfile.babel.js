// Node libraries
const fs = require('fs');

// Gulp system
const gulp = require('gulp-help')(require('gulp'));
const plugins = require('gulp-load-plugins')();
const gutil = require('gulp-util');

// Gulp plugins
const browserify = require('browserify');
const buffer = require('vinyl-buffer');
const rimraf = require('rimraf');
const browserSync = require('browser-sync');
const pngquant = require('imagemin-pngquant');
const runSequence = require('run-sequence');
const source = require('vinyl-source-stream');
const uglify = require('gulp-uglify');
const watchify = require('watchify');
const eslint = require('gulp-eslint');
const LessPluginAutoPrefix = require('less-plugin-autoprefix');
const autoprefix = new LessPluginAutoPrefix({ browsers: ['last 2 versions'] });

function noop() {}

// Error handling
function handleError(taskName, error) {
  return gutil.log(error);
}

// Browserify
function getBundle(path) {
  return browserify({
    entries: path,
    // TODO: Find a good way not to do this in production.
    debug: true,
    transform: [],
  });
}

function runBundle() {
  return (bundle, cb) => {
    bundle.bundle()
      .on('error', (error) => {
        handleError(error);
        this.emit('end');
      })
      .pipe(source('app.bundle.js'))
      .pipe(gulp.dest('build/js'))
      .on('end', cb)
      .pipe(browserSync.reload({ stream: true }));
  };
}

gulp.task(
  'cleanDirectory',
  'Cleans the build directory, starting every run with a clean slate.',
  (cb) => rimraf('build/*', cb)
);

gulp.task(
  'compileBabel',
  'Compiles all JavaScript files from ES2015 to ES5 with Babel.js from the source directory to the build directory.',
  (cb) => gulp.src('src/**/*.js')
    .pipe(plugins.plumber({
      errorHandler: (error) => {
        handleError(error);
        this.emit('end');
      },
    }))
    .pipe(plugins.changed('build'))
    // TODO: Doesn't work.
    .pipe(plugins.sourcemaps.init())
    .pipe(plugins.babel({
      presets: ['es2015', 'react', 'stage-0'],
    }))
    .pipe(plugins.sourcemaps.write())
    .pipe(gulp.dest('build'))
);

gulp.task(
  'compileBrowserify',
  'Compiles the client JavaScript code from the build directory into one file in the build directory with Browserify.',
  (cb) => {
    const path = 'build/js/app.js';

    fs.exists(path, (doesExist) => {
      if (!doesExist) {
        return cb();
      }

      const bundle = getBundle(path);

      runBundle()(bundle, (error) => {
        if (error) {
          return cb(error);
        }

        cb();
      });
    });
  }
);

gulp.task(
  'compileImages',
  'Minifies all GIF, JPG, PNG and SVG images from the source directory into the build directory.',
  () => gulp.src('src/**/*.@(gif|jpg|png|svg)')
    .pipe(plugins.changed('build'))
    .pipe(plugins.imagemin({
      progressive: true,
      svgoPlugins: [{ removeViewbox: false }],
      use: [pngquant()],
    }))
    .pipe(gulp.dest('build'))
    .pipe(browserSync.reload({
      stream: true,
    }))
);

gulp.task(
  'compileLess',
  'Compiles all LESS files to CSS from the source directory to the build directory.',
  () => gulp.src('src/styles/app.less')
    .pipe(plugins.changed('build'))
    .pipe(plugins.less({
      plugins: [autoprefix],
    }))
    .pipe(plugins.cssnano())
    .pipe(gulp.dest('build/css'))
    .pipe(browserSync.reload({
      stream: true,
    }))
);

gulp.task(
  'compileCopy',
  'Copies over all files that are not compiled by another task from the source directory to the build directory.',
  () => gulp.src('src/**/*.!(gif|jpg|png|svg|less|js)')
    .pipe(plugins.changed('build'))
    .pipe(gulp.dest('build'))
    .pipe(browserSync.reload({
      stream: true,
    }))
);

gulp.task(
  'minifyJavaScript',
  'Minifies the client javascript bundle generated by Browserify.',
  () => gulp.src('build/js/app.bundle.js')
    .pipe(uglify())
    .pipe(gulp.dest('build/js'))
);

gulp.task(
  'minifyHTML',
  'Minifies all HTML files and puts them in the build directory.',
  () => gulp.src('build/**/*.html')
    .pipe(plugins.htmlmin({
      removeComments: true,
      removeCommentsFromCDATA: true,
      removeCDATASectionsFromCDATA: true,
      removeAttributeQuotes: true,
      removeRedundantAttributes: true,
      useShortDoctype: true,
      collapseWhitespace: true,
    }))
    .pipe(gulp.dest('build'))
);

gulp.task(
  'modifyIndexHTML',
  'Modifies the index.html so some things are not loaded in production.',
  (cb) => {
    fs.readFile('build/index.html', (error, content) => {
      if (error) {
        return cb(error);
      }

      const newContent = content.toString().replace('var NODE_ENV = \'development\';', 'var NODE_ENV = \'production\';');

      fs.writeFile('build/index.html', newContent, (error) => {
        if (error) {
          return cb(error);
        }

        cb();
      });
    });
  }
);

gulp.task(
  'runBrowsersyncServer',
  'Runs a `live-server` HTTP server, serving all of the client side files from the build directory.',
  (cb) => {
    browserSync();
    cb();
  }
);

gulp.task(
  'runTests',
  'Runs all tests from the source directory with Mocha.',
  () => gulp.src('test/**/*.js')
    .pipe(plugins.mocha({
      recursive: true,
      reporter: 'spec',
      require: 'test/setup.js',
      compiler: {
        js: 'babel',
      },
    }))
);

gulp.task(
  'watchBrowserify',
  'Watches the build directory for new files that get compiled to there and re-compiles the client code with browserify when that happens.',
  (cb) => {
    const path = 'build/js/app.js';

    fs.exists(path, (doesExist) => {
      if (!doesExist) {
        return cb();
      }

      const bundle = watchify(getBundle(path));

      bundle.on('update', () => {
        runBundle()(bundle, noop);
      });

      bundle.on('log', gutil.log);

      runBundle()(bundle, noop);

      cb();
    });
  }
);

gulp.task(
  'watchCompilers',
  'Watches the source directory and runs compile tasks when changes happen.',
  (cb) => {
    plugins.saneWatch('src/**/*', { debounce: 300 }, (filename, path, stat) => {
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
  }
);

gulp.task(
  'watchTests',
  'Watches the source tests directory and re-runs tests when changes happen.',
  (cb) => {
    gulp.watch('test/**/*.js', ['runTests']);

    cb();
  }
);

gulp.task(
  'lint',
  'Lints all the Javascript sources',
  () => gulp.src('src/js/**/*.js')
    .pipe(eslint())
    .pipe(eslint.format())
);

gulp.task(
  'develop',
  'Runs all tasks required for development.',
  (cb) => {
    runSequence(
      'cleanDirectory',

      [
        'lint',
        'compileBabel',
        'compileCopy',
        'compileImages',
        'compileLess',
      ],

      'runTests',

      [
        'runBrowsersyncServer',
        'watchBrowserify',
        'watchTests',
        'watchCompilers',
      ],

      cb
    );
  }
);

gulp.task(
  'production',
  'Runs all tasks required for production.',
  (cb) => {
    runSequence(
      'cleanDirectory',

      [
        'lint',
        'compileBabel',
        'compileCopy',
        'compileImages',
        'compileLess',
      ],

      'compileBrowserify',
      'minifyJavaScript',

      'modifyIndexHTML',
      'minifyHTML',

      cb
    );
  }
);
