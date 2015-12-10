import log from '../../../common/lib/log';

export default function promiseFromNodeCallback(cb, ...args) {
  return new Promise((resolve, reject) => {
    cb(...args, (error, ...args) => {
      if (error) {
        return reject(error);
      }

      resolve(args);
    });
  });
}
