const { combineReducers } = require('redux');

const defaults = {
  user: {
    isFetching: false,

    id: '123',

    new: {
      title: 'Senior Node.js Developer',

      name: 'Tom',
      surname: 'Wieland',
      email: 'tom.wieland@gmail.com',
      phonenumber: '+31642116830',
      github: 'https://github.com/Industrial',
      linkedin: null,
      dateOfBirth: new Date('1987-04-22T00:00:00.000Z'),
      nationality: 'DUTCH',
      aboutMe: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi magna ante, convallis vel auctor sed, tristique nec velit. In lacinia vitae massa at porttitor. In pretium justo urna, sit amet vulputate ipsum interdum vel. Morbi pretium diam ac leo laoreet, in aliquet mi iaculis. Donec a tempor neque.',

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
          title: 'Senior Engineer',
          location: 'Haarlem',
          startDate: new Date('2015-12-02T00:00:00.000Z'),
          endDate: new Date('2016-06-31T00:00:00.000Z'),
          shortDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi magna ante, convallis vel auctor sed, tristique nec velit. In lacinia vitae massa at porttitor. In pretium justo urna, sit amet vulputate ipsum interdum vel. Morbi pretium diam ac leo laoreet, in aliquet mi iaculis. Donec a tempor neque.',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        },
        {
          companyName: 'Sytac',
          title: 'Medior Engineer',
          location: 'Haarlem',
          startDate: new Date('2015-01-02T00:00:00.000Z'),
          endDate: new Date('2016-06-31T00:00:00.000Z'),
          shortDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi magna ante, convallis vel auctor sed, tristique nec velit. In lacinia vitae massa at porttitor. In pretium justo urna, sit amet vulputate ipsum interdum vel. Morbi pretium diam ac leo laoreet, in aliquet mi iaculis. Donec a tempor neque.',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        },
        {
          companyName: 'Sytac',
          title: 'Junior Engineer',
          location: 'Haarlem',
          startDate: new Date('2015-12-02T00:00:00.000Z'),
          endDate: new Date('2016-12-31T00:00:00.000Z'),
          shortDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi magna ante, convallis vel auctor sed, tristique nec velit. In lacinia vitae massa at porttitor. In pretium justo urna, sit amet vulputate ipsum interdum vel. Morbi pretium diam ac leo laoreet, in aliquet mi iaculis. Donec a tempor neque.',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        }
      ],

      languages: [
        {
          name: 'Dutch',
          proficiency: 'NATIVE'
        },
        {
          name: 'English',
          proficiency: 'FULL_PROFESSIONAL'
        }
      ]
    },

    item: {
      title: 'IT Consultant',

      name: 'Tom',
      surname: 'Wieland',
      email: 'tom.wieland@gmail.com',
      phonenumber: '+31642116830',
      github: 'https://github.com/Industrial',
      linkedin: null,
      dateOfBirth: new Date('1987-04-22T00:00:00.000Z'),
      nationality: 'DUTCH',
      aboutMe: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi magna ante, convallis vel auctor sed, tristique nec velit. In lacinia vitae massa at porttitor. In pretium justo urna, sit amet vulputate ipsum interdum vel. Morbi pretium diam ac leo laoreet, in aliquet mi iaculis. Donec a tempor neque.',

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
          endDate: new Date('2016-06-31T00:00:00.000Z'),
          shortDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi magna ante, convallis vel auctor sed, tristique nec velit. In lacinia vitae massa at porttitor. In pretium justo urna, sit amet vulputate ipsum interdum vel. Morbi pretium diam ac leo laoreet, in aliquet mi iaculis. Donec a tempor neque.',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        },
        {
          companyName: 'Sytac',
          title: 'Engineer',
          location: 'Haarlem',
          startDate: new Date('2015-01-02T00:00:00.000Z'),
          endDate: new Date('2016-06-31T00:00:00.000Z'),
          shortDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi magna ante, convallis vel auctor sed, tristique nec velit. In lacinia vitae massa at porttitor. In pretium justo urna, sit amet vulputate ipsum interdum vel. Morbi pretium diam ac leo laoreet, in aliquet mi iaculis. Donec a tempor neque.',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        },
        {
          companyName: 'Sytac',
          title: 'Engineer',
          location: 'Haarlem',
          startDate: new Date('2015-12-02T00:00:00.000Z'),
          endDate: new Date('2016-12-31T00:00:00.000Z'),
          shortDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi magna ante, convallis vel auctor sed, tristique nec velit. In lacinia vitae massa at porttitor. In pretium justo urna, sit amet vulputate ipsum interdum vel. Morbi pretium diam ac leo laoreet, in aliquet mi iaculis. Donec a tempor neque.',
          technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
          methodologies: ['Waterfall', 'Pray n Spray']
        }
      ],

      languages: [
        {
          name: 'Dutch',
          proficiency: 'NATIVE'
        },
        {
          name: 'English',
          proficiency: 'FULL_PROFESSIONAL'
        }
      ]
    },

    lastUpdated: null
  }
};

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
  user
});
