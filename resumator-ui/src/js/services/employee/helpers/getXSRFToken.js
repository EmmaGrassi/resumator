
export default function getXSRFToken() {
  return document.getElementById('xsrf-token').innerHTML;
}
