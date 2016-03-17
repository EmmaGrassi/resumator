import moment from 'moment';
import phone from 'phone';

export default function sanitizeEmployee(data) {
  data.type = data.type || 'PROSPECT';
  data.courses = data.courses || [];
  data.education = data.education || [];
  data.experience = data.experience || [];
  data.languages = data.languages || [];

  if (data.phonenumber) {
    const withDutchCountryCode = data.phonenumber.substr(0, 2) === '06' ?
      data.phonenumber.replace('06', '+316') :
      data.phonenumber;

    data.phonenumber = phone(withDutchCountryCode)[0] || data.phonenumber;
    console.log(data.phonenumber);
  }

  data.experience = data.experience.map(exp => {
    if (!exp.endDate || exp.currentlyWorkHere === 'on') {
      exp.endDate = null;
    }

    delete exp.currentlyWorkHere;
    return exp;
  });

  data.dateOfBirth = data.dateOfBirth && moment(data.dateOfBirth).format('YYYY-MM-DD');

  return data;
}
