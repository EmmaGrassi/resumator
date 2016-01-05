const user = {
  login: {
    start: (data) => {
      return { type: 'user:login:start', data };
    },

    success: (data) => {
      document.cookie = "resumatorJWT=" + data.token;

      return { type: 'user:login:success', data };
    },

    error: (data) => {
      return { type: 'user:login:error', data };
    }
  }
};

const employee = {
  new: (data) => {
    return { type: 'employee:new', data };
  }
};

module.exports = {
  employee,
  user
};
