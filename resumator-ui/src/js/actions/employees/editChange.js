export default function change(key, value) {
  return { type: 'employees:edit:change', payload: { key, value } };
}
