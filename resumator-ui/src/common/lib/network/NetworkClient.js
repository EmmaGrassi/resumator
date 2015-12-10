import { EventEmitter } from 'events';

import Adapter from 'strong-pubsub-redis';
import Client from 'strong-pubsub';
// import PrimusTransport from 'strong-pubsub-primus';
import log from 'loglevel';

class NetworkClient extends EventEmitter {
  constructor(options) {
    log.debug('NetworkClient#constructor', options);

    super(options);

    this.pubsub = new Client({
      host: options.host,
      port: options.port
    }, Adapter, PrimusTransport);

    this.pubsub.on('connect', this.onSocketConnect.bind(this));

    // Doesn't happen.
    // this.pubsub.on('disconnect', this.onSocketDisconnect.bind(this));

    this.connected = false;
  }

  _ensureConnect(method, fn) {
    log.debug('NetworkClient#_ensureConnect', method);

    if (this.connected) {
      fn();
    } else {
      this[method]('connect', fn);
    }
  }

  onSocketConnect() {
    log.debug('NetworkClient#onSocketConnect');

    this.connected = true;

    this.emit('connect');
  }

  /*
  onSocketDisconnect() {
    log.debug('NetworkClient#onSocketDisconnect');

    this.connected = false;

    this.emit('disconnect');
  }
  */

  onConnect(fn) {
    log.debug('NetworkClient#onConnect');

    this._ensureConnect('on', fn);
  }

  onceConnect(fn) {
    log.debug('NetworkClient#onceConnect');

    this._ensureConnect('once', fn);
  }

  start() {
    this.pubsub.connect();
  }
}

export default NetworkClient;
