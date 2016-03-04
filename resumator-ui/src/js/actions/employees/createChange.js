export default function change(key, value) {
  return { type: 'employees:create:change', payload: { key, value } };
}
