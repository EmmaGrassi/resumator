export default function addEntry(type) {
  return (dispatch) => {
    dispatch({ type: 'employees:addEntry', payload: type });
  };
}
