const { EventEmitter } = require('events');

const Adapter = require('strong-pubsub-redis');
const Client = require('strong-pubsub');
// const PrimusTransport = require('strong-pubsub-primus');
const log = require('loglevel');

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

module.exports = NetworkClient;
