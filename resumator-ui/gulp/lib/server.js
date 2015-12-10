import { exists } from 'fs';

import browserSync from 'browser-sync';

export function runServer(gulp, plugins, defaults) {
  return cb => {
    const path = `${defaults.paths.target}/server/app.js`;

    exists(path, (doesExist) => {
      if (!doesExist) {
        return cb();
      }

      plugins.developServer.listen({
        path: path
      });

      plugins.saneWatch(`${defaults.paths.target}/server/**/*`, { debounce: 300 }, () => {
        plugins.developServer.restart(() => {
          browserSync.reload();
        });
      });

      cb();
    });
  };
}
