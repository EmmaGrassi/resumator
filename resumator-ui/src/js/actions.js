const user = {
  login: {
    success: (data) => {
      document.cookie = `resumatorJWT=${data.token}`;

      return { type: 'user:login:success', data };
    },

    error: (data) => {
      return { type: 'user:login:error', data };
    }
  },

  logout: {
    success: (data) => {
      document.cookie = '';

      return { type: 'user:logout:error', data };
    },

    error: (data) => {
      return { type: 'user:logout:error', data };
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
