const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');

const Employee = require('./models/employee');

const app = express();

app.use(bodyParser.json());

mongoose.connect('mongodb://localhost/resumator');

function handleError(res, error) {
  res
    .status(500)
    .send({
      error: error
    });
}

app.get('/api/employees', (req, res) => {
  Employee.find({}, (error, data) => {
    if (error) {
      return handleError(res, error);
    }

    res
      .status(200)
      .send(data);
  });
});

app.post('/api/employees', (req, res) => {
  const model = new Employee(req.body);

  model.save((error, data) => {
    if (error) {
      return handleError(res, error);
    }

    res
      .status(201)
      .send(data);
  });
});

app.get('/api/employees/:id', (req, res) => {
  const { id } = req.params;

  Employee.findOne(id, (error, data) => {
    if (error) {
      return handleError(res, error);
    }

    res
      .status(200)
      .send(data);
  });
});

app.put('/api/employees/:id', (req, res) => {
  const { id } = req.params;

  Employee.update({ _id: id }, req.body, (error, data) => {
    if (error) {
      return handleError(res, error);
    }

    res
      .status(204)
      .end();
  });
});

app.delete('/api/employees/:id', (req, res) => {
  const { id } = req.params;

  Employee.remove({ _id: id }, (error, data) => {
    if (error) {
      return handleError(res, error);
    }

    res
      .status(204)
      .end();
  });
});

const server = app.listen(3000, function () {
  const host = server.address().address;
  const port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);
});
