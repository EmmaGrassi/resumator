import immutable from 'immutable';

const defaults = immutable.Map({
  showAlert: false,
  alerts: immutable.List.of(),
});

function create(state = defaults, action = {}) {
  switch (action.type) {
    case 'alerts:show':
      return state
        .set('showAlert', true)
        .set('alerts', state.get('alerts').push(action.payload));

    case 'alerts:hide':
      return state
        .set('showAlert', (state.get('alerts').count() > 1))
        .set('alerts', state.get('alerts').filter(alert => alert.id !== action.payload.id));

    default:
      return state;
  }
}

export default create;
