
import store from '../../store';

export default function hide(alert) {
  return (dispatch) => dispatch({ type: 'alerts:hide', payload: alert });
}
