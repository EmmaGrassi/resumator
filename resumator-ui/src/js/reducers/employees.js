import immutable from 'immutable';

const item = immutable.Map({
  title: null,

  name: null,
  surname: null,
  email: null,
  phonenumber: null,
  github: null,
  linkedin: null,
  dateOfBirth: null,
  nationality: null,
  aboutMe: null,

  education: immutable.List([
    //{
    //  degree: 'Bachelor of ICT',
    //  fieldOfStudy: 'ICT',
    //  university: 'Hogeschool van Amsterdam',
    //  graduated: true,
    //  graduationYear: 1999
    //}
  ]),

  courses: immutable.List([
    //{
    //  name: 'Some',
    //  description: 'Description',
    //  date: new Date('1976-02-01T00:00:00.000Z')
    //}
  ]),

  experience: immutable.List([
    //{
    //  companyName: 'Sytac',
    //  title: 'Senior Engineer',
    //  location: 'Haarlem',
    //  startDate: new Date('2015-12-02T00:00:00.000Z'),
    //  endDate: new Date('2016-06-31T00:00:00.000Z'),
    //  shortDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi magna ante, convallis vel auctor sed, tristique nec velit. In lacinia vitae massa at porttitor. In pretium justo urna, sit amet vulputate ipsum interdum vel. Morbi pretium diam ac leo laoreet, in aliquet mi iaculis. Donec a tempor neque.',
    //  technologies: ['Jquery', 'MSSQL', 'Word', 'QUICKBASIC'],
    //  methodologies: ['Waterfall', 'Pray n Spray']
    //}
  ]),

  languages: immutable.List([
    //{
    //  name: 'Dutch',
    //  proficiency: 'NATIVE'
    //}
  ])
});

const defaults = immutable.Map({
  list: immutable.Map({
    isFetching: false,

    items: immutable.List()
  }),

  create: immutable.Map({
    isSaving: false,

    item: item
  }),

  show: immutable.Map({
    isFetching: false,

    item: item
  }),

  edit: immutable.Map({
    isFetching: false,

    item: item
  })
});

function list(state = defaults, action = {}) {
  switch (action.type) {
    case 'employees:list:start':
      return state
        .setIn(['list', 'isFetching'], true);

    case 'employees:list:success':
      return state
        .setIn(['list', 'isFetching'], false)
        .setIn(['list', 'items'], immutable.fromJS(action.response));

    case 'employees:list:failure':
      return state
        .setIn(['list', 'isFetching'], false);


    case 'employees:create:start':
      return state
        .setIn(['create', 'isSaving'], true);

    case 'employees:create:success':
      return state
        .setIn(['create', 'isSaving'], false);

    case 'employees:create:failure':
      return state
        .setIn(['create', 'isSaving'], false);


    case 'employees:show:start':
      return state
        .setIn(['show', 'isFetching'], true);

    case 'employees:show:success':
      return state
        .setIn(['show', 'isFetching'], false)
        .setIn(['show', 'item'], immutable.fromJS(action.response));

    case 'employees:show:failure':
      return state
        .setIn(['show', 'isFetching'], false);


    case 'employees:edit:start':
      return state
        .setIn(['edit', 'isFetching'], true);

    case 'employees:edit:success':
      return state
        .setIn(['edit', 'isFetching'], false)
        .setIn(['edit', 'item'], immutable.fromJS(action.response));

    case 'employees:edit:failure':
      return state
        .setIn(['edit', 'isFetching'], false);


    default:
      return state;
  }
}

export default list;
