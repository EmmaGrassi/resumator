export default function promiseFromCallback(cb, ...cbArgs) {
  return new Promise(resolve => { cb(...cbArgs, resolve); });
};
