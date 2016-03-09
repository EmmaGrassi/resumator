import moment from 'moment';

export default function sanitizeEmployee(data) {
  data.type = data.type || 'PROSPECT';
  data.courses = data.courses || [];
  data.education = data.education || [];
  data.experience = data.experience || [];
  data.languages = data.languages || [];

  data.dateOfBirth = data.dateOfBirth && (data.dateOfBirth).format('YYYY-MM-DD');

  return data;
}
