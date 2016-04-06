Request:
  {
  	"cityOfResidence": "Amsterdam",
  	"aboutMe": "<div>Some stuff here.</div>",
  	"education": [{
  		"degree": "BACHELOR_DEGREE",
  		"school": "Hogeschool van Amsterdam",
  		"fieldOfStudy": "IT",
  		"country": "NL",
  		"city": "Amsterdam",
  		"startYear": "2008",
  		"endYear": "2010"
  	}],
  	"experience": [{
  		"shortDescription": "<div>WHEEEE</div>",
  		"methodologies": ["SCRUM"],
  		"companyName": "Sytac",
  		"endDate": "2020-12-21",
  		"technologies": ["Node.js", "React"],
  		"city": "Haarlem",
  		"startDate": "2015-12-02",
  		"role": "Full-Stack Consultant",
  		"country": "NL"
  	}],
  	"name": "Tom",
  	"admin": true,
  	"role": "Consultant",
  	"phonenumber": "1234567890",
  	"courses": [{
  		"name": "Some Course",
  		"year": "2001",
  		"description": "Yeah, man."
  	}],
  	"countryOfResidence": "NL",
  	"dateOfBirth": "1987-04-22",
  	"type": "PROSPECT",
  	"languages": [{
  		"name": "Dutch",
  		"proficiency": "NATIVE"
  	}, {
  		"name": "English",
  		"proficiency": "FULL_PROFESSIONAL"
  	}],
  	"surname": "Wieland",
  	"nationality": "DUTCH",
  	"email": "tom.wieland@sytac.io"
  }

Response:
  {
  	"_links": {
  		"employee": [{
  			"href": "http://resumator-local.sytac.io:9000/api/employees/tom.wieland@sytac.io"
  		}]
  	},
  	"email": "tom.wieland@sytac.io",
  	"status": "updated"
  }
