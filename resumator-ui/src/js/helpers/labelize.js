
export default function labelize(name) {
  return name.replace(/([A-Z])/g, ' $1')
    .replace(/^./, str => str.toUpperCase());
}
