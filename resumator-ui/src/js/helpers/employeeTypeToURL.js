export default function employeeTypeToURL(type) {
  if (type === 'EMPLOYEE') {
    return 'employees';
  }

  if (type === 'FREELANCER') {
    return 'freelancers';
  }

  if (type === 'PROSPECT') {
    return 'prospects';
  }
}
