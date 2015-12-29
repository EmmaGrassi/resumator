const { combineReducers } = require('redux');

const log = require('../lib/log');

const defaults = {
  authentication: {
    isFetching: false,

    token: null,
    userId: null,
    ttl: null,
    created: null
  },

  user: {
    isFetching: false,

    id: '123',

    new: {
      name: 'Tom',
      surname: 'Wieland',
      email: 'tom.wieland@gmail.com',
      phonenumber: '+31642116830',
      github: 'https://github.com/Industrial',
      linkedin: null,
      dateOfBirth: new Date('1987-04-22T00:00:00.000Z'),
      nationality: 'DUTCH',
      aboutMe: 'This is a little text about me etc 123.',

      education: [
        {
          degree: 'Bachelor of ICT',
          fieldOfStudy: 'ICT',
          university: 'Hogeschool van Amsterdam',
          graduated: true,
          graduationYear: 1999
        },
        {
          degree: 'Bachelor of ICT',
          fieldOfStudy: 'ICT',
          university: 'Hogeschool van Amsterdam',
          graduated: true,
          graduationYear: 1999
        },
        {
          degree: 'Bachelor of ICT',
          fieldOfStudy: 'ICT',
          university: 'Hogeschool van Amsterdam',
          graduated: true,
          graduationYear: 1999
        }
      ],

      courses: [
        {
          name: 'Some',
          description: 'Description',
          date: new Date('1976-02-01T00:00:00.000Z')
        },
        {
          name: 'Some',
          description: 'Description',
          date: new Date('1976-02-01T00:00:00.000Z')
        },
        {
          name: 'Some',
          description: 'Description',
          date: new Date('1976-02-01T00:00:00.000Z')
        }
      ],

      experience: [
        {
          companyName: 'Sytac',
          title: 'Engineer',
          location: 'Haarlem',
          startDate: new Date('2015-12-02T00:00:00.000Z'),
          endDate: new Date('2016-12-31T00:00:00.000Z'),
          shortDescription: 'Some Description',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        },
        {
          companyName: 'Sytac',
          title: 'Engineer',
          location: 'Haarlem',
          startDate: new Date('2015-12-02T00:00:00.000Z'),
          endDate: new Date('2016-12-31T00:00:00.000Z'),
          shortDescription: 'Some Description',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        },
        {
          companyName: 'Sytac',
          title: 'Engineer',
          location: 'Haarlem',
          startDate: new Date('2015-12-02T00:00:00.000Z'),
          endDate: new Date('2016-12-31T00:00:00.000Z'),
          shortDescription: 'Some Description',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        }
      ]
    },

    item: {
      name: 'Tom',
      surname: 'Wieland',
      email: 'tom.wieland@gmail.com',
      phonenumber: '+31642116830',
      github: 'https://github.com/Industrial',
      linkedin: null,
      dateOfBirth: new Date('1987-04-22T00:00:00.000Z'),
      nationality: 'DUTCH',
      aboutMe: 'This is a little text about me etc 123.',

      education: [
        {
          degree: 'Bachelor of ICT',
          fieldOfStudy: 'ICT',
          university: 'Hogeschool van Amsterdam',
          graduated: true,
          graduationYear: 1999
        },
        {
          degree: 'Bachelor of ICT',
          fieldOfStudy: 'ICT',
          university: 'Hogeschool van Amsterdam',
          graduated: true,
          graduationYear: 1999
        },
        {
          degree: 'Bachelor of ICT',
          fieldOfStudy: 'ICT',
          university: 'Hogeschool van Amsterdam',
          graduated: true,
          graduationYear: 1999
        }
      ],

      courses: [
        {
          name: 'Some',
          description: 'Description',
          date: new Date('1976-02-01T00:00:00.000Z')
        },
        {
          name: 'Some',
          description: 'Description',
          date: new Date('1976-02-01T00:00:00.000Z')
        },
        {
          name: 'Some',
          description: 'Description',
          date: new Date('1976-02-01T00:00:00.000Z')
        }
      ],

      experience: [
        {
          companyName: 'Sytac',
          title: 'Engineer',
          location: 'Haarlem',
          startDate: new Date('2015-12-02T00:00:00.000Z'),
          endDate: new Date('2016-12-31T00:00:00.000Z'),
          shortDescription: 'Some Description',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        },
        {
          companyName: 'Sytac',
          title: 'Engineer',
          location: 'Haarlem',
          startDate: new Date('2015-12-02T00:00:00.000Z'),
          endDate: new Date('2016-12-31T00:00:00.000Z'),
          shortDescription: 'Some Description',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        },
        {
          companyName: 'Sytac',
          title: 'Engineer',
          location: 'Haarlem',
          startDate: new Date('2015-12-02T00:00:00.000Z'),
          endDate: new Date('2016-12-31T00:00:00.000Z'),
          shortDescription: 'Some Description',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        }
      ]
    },

    lastUpdated: null
  }
};

function authentication(state = defaults.authentication, action = {}) {
  let response;

  switch (action.type) {
    case 'authentication:get':
      return Object.assign({}, defaults.authentication, action.authentication);

    case 'request:login:request':
      return Object.assign({}, state, {
        isFetching: true
      });

    case 'request:login:response':
      response = action.args[0];

      return Object.assign({}, state, {
        isFetching: false,

        token: response.body.id,
        userId: response.body.userId,
        ttl: response.body.ttl,
        created: response.body.created
      });

    case 'request:login:error':
      return Object.assign({}, defaults.authentication);

    case 'request:logout:request':
      return Object.assign({}, defaults.authentication);

    case 'request:logout:error':
      return Object.assign({}, defaults.authentication);

    default:
      return state;
  }
}

function user(state = defaults.user, action = {}) {
  switch (action.type) {
    case 'employee:new':
      return Object.assign({}, state, {
        new: action.data
      });

    // case USER:
    //   return state;

    //case 'request:logout':
    //  return Object.assign({}, state, defaults.user);

    default:
      return state;
  }
}

module.exports = combineReducers({
  authentication,
  user
});
