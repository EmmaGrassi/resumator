const path = require('path');

module.exports = {
  plugins: [
  ],
  module: {
    loaders: [
      {
        test: /\.css?$/,
        loaders: ['style', 'raw'],
        include: path.resolve(__dirname, '../'),
      },
      {
        test: /\.less$/,
        loader: 'style!css!less',
      },
    ],
  },
};
