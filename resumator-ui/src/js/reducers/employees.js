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
  ]),

  courses: immutable.List([
  ]),

  experience: immutable.List([
  ]),

  languages: immutable.List([
  ])
});

const defaults = immutable.Map({
  list: immutable.Map({
    isFetching: false,

    items: immutable.List()
  }),

  create: immutable.Map({
    isSaving: false,

    id: null,

    item: item
  }),

  show: immutable.Map({
    isFetching: false,

    item: item
  }),

  preview: immutable.Map({
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
      const id = action.response.id;

      return state
        .setIn(['create', 'isSaving'], false)
        .setIn(['create', 'id'], id);

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


    case 'employees:preview:start':
      return state
        .setIn(['preview', 'isFetching'], true);

    case 'employees:preview:success':
      return state
        .setIn(['preview', 'isFetching'], false)
        .setIn(['preview', 'item'], immutable.fromJS(action.response));

    case 'employees:preview:failure':
      return state
        .setIn(['preview', 'isFetching'], false);


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
