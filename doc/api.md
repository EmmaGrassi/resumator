```
Employee
  Type    Name                    Required  Fields                Validations
  String        id                No
  String        type              Yes       EMPLOYEE
                                            FREELANCER
                                            PROSPECT
  String        role              Yes
  String        name              Yes
  String        surname           Yes
  String        email             Yes
  String        phoneNumber       Yes
  String        gitHub            No
  String        linkedIn          No
  String        dateOfBirth       Yes                             Must have the correct format (YYYY-MM-DD).
                                                                  Must not be in the future.
  String        nationality       Yes                             Must be one in the list (big list).
  String        currentResidence  Yes
  String        aboutMe           Yes
  [Education]   educations        No
  [Course]      courses           No
  [Experience]  experiences       No
  [Language]    languages         No
  Boolean       admin             Yes

Education
  Degree        degree            Yes       ASSOCIATE_DEGREE
                                            BACHELOR_DEGREE
                                            MASTER_DEGREE
                                            ENGINEER_DEGREE
                                            DOCTORAL
                                            OTHER
  String        fieldOfStudy      Yes
  String        school            Yes
  String        city              Yes
  String        country           Yes
  String        startYear         No                              Must not be before 1960.
                                                                  Must not be after 2030.
                                                                  Must not be after endYear
  String        endYear           No                              Must not be before startYear

Course
  String        name              Yes
  String        description       Yes
  String        year              Yes                             Must not be in the future.

Experience
  String        companyName       Yes
  String        role              Yes
  String        city              Yes
  String        country           Yes
  String        shortDescription  Yes
  [String]      technologies      Yes
  [String]      methodologies     Yes
  String        startDate         Yes                             Must not be in the future.
  String        endDate           No

Language
  String        name              Yes       ELEMENTARY
                                            LIMITED_WORKING
                                            PROFESSIONAL_WORKING
                                            FULL_PROFESSIONAL
                                            NATIVE
  String        proficiency       Yes
```  
