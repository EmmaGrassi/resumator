import promiseFromNodeCallback from '../promise/promiseFromNodeCallback';
import httpRequest from 'request';

import log from 'loglevel';

import * as strings from '../strings';

function requestError(options, event) {
  return (error) => {
    return { type: `${event}:error`, error };
  };
}

function requestRequest(options, event) {
  return (...args) => {
    return { type: `${event}:request`, args };
  };
}

function requestResponse(options, event) {
  return (...args) => {
    return { type: `${event}:response`, args };
  };
}

// Returns an object containing the request, response, error and full request
// actions for handling a http request according to the provided options.

// TODO: Add helpers to execute (synchronous) callbacks before other
//       functionality;
//       - beforeRequest
//       - afterRequest
//       - beforeResponse
//       - afterResponse
//       - beforeError
//       - afterError

export function request(options = {}) {
  const exported = {};
  const eventName = `request:${strings.getEventNameFromSnakeCaseFunctionName(options.name)}`;

  const errorAction = requestError(options, eventName);
  const requestAction = requestRequest(options, eventName);
  const responseAction = requestResponse(options, eventName);

  exported[`${options.name}Error`] = errorAction;
  exported[`${options.name}Request`] = requestAction;
  exported[`${options.name}Response`] = responseAction;

  exported[`${options.name}`] = (...args) => {
    return dispatch => {
      dispatch(requestAction(...args));

      return promiseFromNodeCallback(httpRequest, options.getOptions(...args))
        .then(([ response ]) => {
          const responseJson = response.toJSON();

          dispatch(responseAction(responseJson));
          dispatch({ type: eventName });

          return response;
        })
        .catch(error => {
          dispatch(errorAction(error));
        });
    };
  };

  return exported;
}

