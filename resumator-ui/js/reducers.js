import { combineReducers } from 'redux';

import log from 'loglevel';

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

    id: null,
    item: { },
    lastUpdated: null
  },

  cms: {
    schemas: {
      items: [],
      isFetching: false,
      lastUpdated: null
    },

    models: {
      current: {
        id: null,
        item: { },
        isFetching: false,
        lastUpdated: null
      },

      list: {
        items: [],
        isFetching: false,
        lastUpdated: null
      }
    }
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
    // case USER:
    //   return state;

    case 'request:logout':
      return Object.assign({}, state, defaults.user);

    default:
      return state;
  }
}

function cmsSchemas(state = defaults.cms.schemas, action = {}) {
  switch (action.type) {
    // case 'cms:schemas':
    //   return Object.assign({}, state, action.schemas);

    default:
      return state;
  }
}

function cmsModelsCurrent(state = defaults.cms.models.current, action = {}) {
  let response;

  switch (action.type) {
    case 'request:cms:show:request':
      return Object.assign({}, state, {
        isFetching: true
      });

    case 'request:cms:show:response':
      response = action.args[0];

      return Object.assign({}, state, {
        id: response.body.id,
        item: response.body,
        isFetching: false
      });

    case 'request:cms:show:error':
      return Object.assign({}, defaults.cms.models.show);

    // case 'cms:create:error':
    // case 'cms:create:timeout':
    // case 'cms:create:request':
    // case 'cms:create:response':

    // case 'cms:destroy:error':
    // case 'cms:destroy:timeout':
    // case 'cms:destroy:request':
    // case 'cms:destroy:response':

    case 'request:cms:update:request':
      return Object.assign({}, state, {
        isFetching: true
      });

    case 'request:cms:update:response':
      response = action.args[0];

      return Object.assign({}, state, {
        id: response.body.id,
        item: response.body,
        isFetching: false
      });

    case 'request:cms:update:error':
      return Object.assign({}, defaults.cms.models.show);

    default:
      return state;
  }
}

function cmsModelsList(state = defaults.cms.models.list, action = {}) {
  let response;

  switch (action.type) {
    case 'request:cms:list:request':
      return Object.assign({}, state, {
        isFetching: true
      });

    case 'request:cms:list:response':
      response = action.args[0];

      return Object.assign({}, state, {
        items: response.body,
        isFetching: false
      });

    case 'request:cms:list:error':
      return Object.assign({}, defaults.cms.models.list);

    default:
      return state;
  }
}

const cmsModels = combineReducers({
  current: cmsModelsCurrent,
  list: cmsModelsList
});

const cms = combineReducers({
  models: cmsModels,
  schemas: cmsSchemas
});

export default combineReducers({
  authentication,
  user,
  cms
});
