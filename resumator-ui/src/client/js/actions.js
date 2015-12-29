function newEmployee(data) {
  return { type: 'employee:new', data };
};

function clearAuthentication() {
  localStorage.setItem('authentication', null);

  return { type: 'authentication:clear' };
}

function getAuthentication() {
  let authentication = localStorage.getItem('authentication');

  if (authentication) {
    authentication = JSON.parse(authentication);
  }

  return { type: 'authentication:get', authentication };
}

function setAuthentication(authentication) {
  localStorage.setItem('authentication', JSON.stringify(authentication));

  return { type: 'authentication:set' };
}

function getHost() {
  const { protocol, hostname, port } = window.location;

  return `${protocol}//${hostname}:${port}`;
}

module.exports = {
  newEmployee,
  clearAuthentication,
  getAuthentication,
  setAuthentication
};
