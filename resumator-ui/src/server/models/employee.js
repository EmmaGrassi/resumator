const mongoose = require('mongoose');

const EmployeeSchema = mongoose.Schema({
  title: String,
  name: {
    type: String
  },
  surname: {
    type: String
  },
  email: {
    type: String
  },
  phonenumber: {
    type: String
  },
  github: {
    type: String
  },
  linkedin: {
    type: String
  },
  dateOfBirth: {
    type: String
  },
  nationality: {
    type: String
  },
  aboutMe: {
    type: String
  },

  education: [
    {
      degree: {
        type: String
      },
      fieldOfStudy: {
        type: String
      },
      university: {
        type: String
      },
      graduated: {
        type: Boolean
      },
      graduationYear: {
        type: Number
      }
    }
  ],

  courses: [
    {
      name: {
        type: String
      },
      description: {
        type: String
      },
      date: {
        type: Date
      }
    }
  ],

  experience: [
    {
      companyName: {
        type: String
      },
      title: {
        type: String
      },
      location: {
        type: String
      },
      startDate: {
        type: Date
      },
      endDate: {
        type: Date
      },
      shortDescription: {
        type: String
      },
      technologies: {
        type: [String]
      },
      methodologies: {
        type: [String]
      }
    }
  ],

  languages: [
    {
      name: {
        type: String
      },
      proficiency: {
        type: String
      },
    }
  ]
});

const Employee = mongoose.model('Employee', EmployeeSchema);

module.exports = Employee;
