function newEmployee(data) {
  return { type: 'employee:new', data };
};

module.exports = {
  newEmployee
};
