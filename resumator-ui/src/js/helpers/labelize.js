
function prefixLabel(label, prefix) {
  return `${prefix} ${label}`;
}

export default function labelize(name, prefix) {
  const label = name.replace(/([A-Z])/g, ' $1')
    .replace(/^./, str => str.toUpperCase());

  return prefix ? prefixLabel(label, prefix) : label;
}
