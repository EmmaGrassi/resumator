import moment from 'moment';

export default function sanitizeEmployee(data) {
  data.type = data.type || 'EMPLOYEE';
  data.courses = data.courses || [];
  data.education = data.education || [];
  data.experience = data.experience || [];
  data.languages = data.languages || [];

  data.dateOfBirth = moment(data.dateOfBirth).format('YYYY-MM-DD');

  return data;
}
