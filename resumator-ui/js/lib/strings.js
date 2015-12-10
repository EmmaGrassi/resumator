export function isLowerCase(character) {
  return character === character.toLowerCase();
}

export function isUpperCase(character) {
  return character === character.toUpperCase();
}

export function getEventNameFromSnakeCaseFunctionName(functionName) {
  if (isUpperCase(functionName)) {
    return functionName.toLowerCase();
  }

  let eventName = functionName.replace(/([A-Z])/g, (a, l) => {
    return `:${l.toLowerCase()}`;
  });

  if (eventName[0] === ':') {
    eventName = eventName.substring(1);
  }

  return eventName;
}

export function getSnakeCaseFunctionNameFromEventName(eventName) {
  return eventName
    .replace(/:([a-z])/g, (a, l) => {
      return l.toUpperCase();
    })
    .replace(/:/g, '');
}
