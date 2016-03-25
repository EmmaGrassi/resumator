export default function addEntry(key, type) {
  return (dispatch) => {
    dispatch({ type: 'employees:removeEntry', payload: { key, type } });
  };
}
