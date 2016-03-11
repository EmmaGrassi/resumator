
import store from '../../store';
import { pushPath } from 'redux-simple-router';

export default function editCancel() {
  return (dispatch) => {
    const user = store.getState().user;
    const userEmail = user.session.toJS().email;
    const isAdmin = user.profile.toJS().item.admin;
    const redirection = isAdmin ? '/employees' : `/employees/${userEmail}`;

    dispatch({ type: 'employees:edit:cancel' });
    dispatch(pushPath(redirection));
  };
}
