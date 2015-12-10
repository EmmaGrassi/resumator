const keystone = require('keystone');
const shoe = require('shoe');
const dnode = require('dnode');
const muxDemux = require('mux-demux');

export const install = function(server, dnodeInterface) {
  const shoeStream = shoe(function(shoeClientStream) {
    const mdmStream = muxDemux(function(mdmClientStream){
      debug('mdm-client-stream', mdmClientStream);
    });

    const dnodeStream = dnode(dnodeInterface);

    const dnodeMuxdemuxStream = mdmStream.createStream('dnode');

    dnodeMuxdemuxStream
      .pipe(dnodeStream)
      .pipe(dnodeMuxdemuxStream);

    shoeClientStream
      .pipe(mdmStream)
      .pipe(shoeClientStream);

  });

  shoeStream.install(server, '/ws');

  server.shoeStream = shoeStream;
};
