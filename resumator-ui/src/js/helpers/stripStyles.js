
export default function stripStyles(str) {
  return str.replace(/style\=[\"\']?([a-zA-Z0-9 \:\-\#\(\)\.\_\/\;\'\,]+)\;?[\"\']?/ig, '');
}
