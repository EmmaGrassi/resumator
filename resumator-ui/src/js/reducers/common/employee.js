import immutable from 'immutable';

const item = immutable.Map({
  role: null,

  name: null,
  surname: null,
  email: null,
  phonenumber: null,
  currentResidence: null,
  github: null,
  linkedin: null,
  dateOfBirth: null,
  nationality: null,
  aboutMe: null,

  education: immutable.List(),

  courses: immutable.List(),

  experience: immutable.List(),

  languages: immutable.List(),
});

export default item;
