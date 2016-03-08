
import store from '../../store';
import hideAlert from './hide';

export default function show(alert) {
  return (dispatch) => {
    setTimeout(() => {
      dispatch(hideAlert(alert));
    }, 3000);
    dispatch({ type: 'alerts:show', payload: alert });
  };
}
