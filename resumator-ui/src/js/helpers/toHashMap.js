

function toHashMap(arr) {
  return arr.reduce((prev, curr) => {
    prev[curr.value] = curr.label;
    return prev;
  }, {});
}

export default toHashMap;
