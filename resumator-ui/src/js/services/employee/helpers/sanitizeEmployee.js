import moment from 'moment';

export default function sanitizeEmployee(data) {
  data.type = data.type || 'PROSPECT';
  data.courses = data.courses || [];
  data.education = data.education || [];
  data.experience = data.experience || [];
  data.languages = data.languages || [];


  data.experience = data.experience.map(exp => {
    console.log(exp.currentlyWorkHere);
    if (!exp.endDate || exp.currentlyWorkHere === 'on') {
      exp.endDate = null;
    }

    delete exp.currentlyWorkHere;
    return exp;
  });

  data.dateOfBirth = data.dateOfBirth && moment(data.dateOfBirth).format('YYYY-MM-DD');

  return data;
}
