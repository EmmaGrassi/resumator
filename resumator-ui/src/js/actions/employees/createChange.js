export default function change(key, value) {
  return (dispatch) => dispatch({ type: 'employees:create:change', payload: { key, value } });
}
