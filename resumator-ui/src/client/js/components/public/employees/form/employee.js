import React from 'react';
import moment from 'moment';
import qwest from 'qwest';
import { bindAll, map } from 'lodash';

import {
  Button,
  ButtonGroup,
  Col,
  Grid,
  Input,
  Row
} from 'react-bootstrap';
import DateTimeInput from 'react-bootstrap-datetimepicker';
import Select from 'react-select';

import Experience from './experience';
import Education from './education';
import Courses from './courses';

class Employee extends React.Component {
  constructor(options) {
    super(options);

    this.countries = [
      { value: '', label: '' },
      { value: 'AFGHAN', label: 'Afghan' },
      { value: 'ALBANIAN', label: 'Albanian' },
      { value: 'ALGERIAN', label: 'Algerian' },
      { value: 'AMERICAN', label: 'American' },
      { value: 'ANDORRAN', label: 'Andorran' },
      { value: 'ANGOLAN', label: 'Angolan' },
      { value: 'ANTIGUANS', label: 'Antiguans' },
      { value: 'ARGENTINEAN', label: 'Argentinean' },
      { value: 'ARMENIAN', label: 'Armenian' },
      { value: 'AUSTRALIAN', label: 'Australian' },
      { value: 'AUSTRIAN', label: 'Austrian' },
      { value: 'AZERBAIJANI', label: 'Azerbaijani' },
      { value: 'BAHAMIAN', label: 'Bahamian' },
      { value: 'BAHRAINI', label: 'Bahraini' },
      { value: 'BANGLADESHI', label: 'Bangladeshi' },
      { value: 'BARBADIAN', label: 'Barbadian' },
      { value: 'BARBUDANS', label: 'Barbudans' },
      { value: 'BATSWANA', label: 'Batswana' },
      { value: 'BELARUSIAN', label: 'Belarusian' },
      { value: 'BELGIAN', label: 'Belgian' },
      { value: 'BELIZEAN', label: 'Belizean' },
      { value: 'BENINESE', label: 'Beninese' },
      { value: 'BHUTANESE', label: 'Bhutanese' },
      { value: 'BOLIVIAN', label: 'Bolivian' },
      { value: 'BOSNIAN', label: 'Bosnian' },
      { value: 'BRAZILIAN', label: 'Brazilian' },
      { value: 'BRITISH', label: 'British' },
      { value: 'BRUNEIAN', label: 'Bruneian' },
      { value: 'BULGARIAN', label: 'Bulgarian' },
      { value: 'BURKINABE', label: 'Burkinabe' },
      { value: 'BURMESE', label: 'Burmese' },
      { value: 'BURUNDIAN', label: 'Burundian' },
      { value: 'CAMBODIAN', label: 'Cambodian' },
      { value: 'CAMEROONIAN', label: 'Cameroonian' },
      { value: 'CANADIAN', label: 'Canadian' },
      { value: 'CAPE_VERDEAN', label: 'Cape Verdean' },
      { value: 'CENTRAL_AFRICAN', label: 'Central African' },
      { value: 'CHADIAN', label: 'Chadian' },
      { value: 'CHILEAN', label: 'Chilean' },
      { value: 'CHINESE', label: 'Chinese' },
      { value: 'COLOMBIAN', label: 'Colombian' },
      { value: 'COMORAN', label: 'Comoran' },
      { value: 'CONGOLESE', label: 'Congolese' },
      { value: 'COSTA_RICAN', label: 'Costa Rican' },
      { value: 'CROATIAN', label: 'Croatian' },
      { value: 'CUBAN', label: 'Cuban' },
      { value: 'CYPRIOT', label: 'Cypriot' },
      { value: 'CZECH', label: 'Czech' },
      { value: 'DANISH', label: 'Danish' },
      { value: 'DJIBOUTI', label: 'Djibouti' },
      { value: 'DOMINICAN', label: 'Dominican' },
      { value: 'DUTCH', label: 'Dutch' },
      { value: 'EAST_TIMORESE', label: 'East Timorese' },
      { value: 'ECUADOREAN', label: 'Ecuadorean' },
      { value: 'EGYPTIAN', label: 'Egyptian' },
      { value: 'EMIRIAN', label: 'Emirian' },
      { value: 'EQUATORIAL_GUINEAN', label: 'Equatorial Guinean' },
      { value: 'ERITREAN', label: 'Eritrean' },
      { value: 'ESTONIAN', label: 'Estonian' },
      { value: 'ETHIOPIAN', label: 'Ethiopian' },
      { value: 'FIJIAN', label: 'Fijian' },
      { value: 'FILIPINO', label: 'Filipino' },
      { value: 'FINNISH', label: 'Finnish' },
      { value: 'FRENCH', label: 'French' },
      { value: 'GABONESE', label: 'Gabonese' },
      { value: 'GAMBIAN', label: 'Gambian' },
      { value: 'GEORGIAN', label: 'Georgian' },
      { value: 'GERMAN', label: 'German' },
      { value: 'GHANAIAN', label: 'Ghanaian' },
      { value: 'GREEK', label: 'Greek' },
      { value: 'GRENADIAN', label: 'Grenadian' },
      { value: 'GUATEMALAN', label: 'Guatemalan' },
      { value: 'GUINEA_BISSAUAN', label: 'Guinea Bissauan' },
      { value: 'GUINEAN', label: 'Guinean' },
      { value: 'GUYANESE', label: 'Guyanese' },
      { value: 'HAITIAN', label: 'Haitian' },
      { value: 'HERZEGOVINIAN', label: 'Herzegovinian' },
      { value: 'HONDURAN', label: 'Honduran' },
      { value: 'HUNGARIAN', label: 'Hungarian' },
      { value: 'I_KIRIBATI', label: 'I Kiribati' },
      { value: 'ICELANDER', label: 'Icelander' },
      { value: 'INDIAN', label: 'Indian' },
      { value: 'INDONESIAN', label: 'Indonesian' },
      { value: 'IRANIAN', label: 'Iranian' },
      { value: 'IRAQI', label: 'Iraqi' },
      { value: 'IRISH', label: 'Irish' },
      { value: 'ISRAELI', label: 'Israeli' },
      { value: 'ITALIAN', label: 'Italian' },
      { value: 'IVORIAN', label: 'Ivorian' },
      { value: 'JAMAICAN', label: 'Jamaican' },
      { value: 'JAPANESE', label: 'Japanese' },
      { value: 'JORDANIAN', label: 'Jordanian' },
      { value: 'KAZAKHSTANI', label: 'Kazakhstani' },
      { value: 'KENYAN', label: 'Kenyan' },
      { value: 'KITTIAN_AND_NEVISIAN', label: 'Kittian and Nevisian' },
      { value: 'KUWAITI', label: 'Kuwaiti' },
      { value: 'KYRGYZ', label: 'Kyrgyz' },
      { value: 'LAOTIAN', label: 'Laotian' },
      { value: 'LATVIAN', label: 'Latvian' },
      { value: 'LEBANESE', label: 'Lebanese' },
      { value: 'LIBERIAN', label: 'Liberian' },
      { value: 'LIBYAN', label: 'Libyan' },
      { value: 'LIECHTENSTEINER', label: 'Liechtensteiner' },
      { value: 'LITHUANIAN', label: 'Lithuanian' },
      { value: 'LUXEMBOURGER', label: 'Luxembourger' },
      { value: 'MACEDONIAN', label: 'Macedonian' },
      { value: 'MALAGASY', label: 'Malagasy' },
      { value: 'MALAWIAN', label: 'Malawian' },
      { value: 'MALAYSIAN', label: 'Malaysian' },
      { value: 'MALDIVAN', label: 'Maldivan' },
      { value: 'MALIAN', label: 'Malian' },
      { value: 'MALTESE', label: 'Maltese' },
      { value: 'MARSHALLESE', label: 'Marshallese' },
      { value: 'MAURITANIAN', label: 'Mauritanian' },
      { value: 'MAURITIAN', label: 'Mauritian' },
      { value: 'MEXICAN', label: 'Mexican' },
      { value: 'MICRONESIAN', label: 'Micronesian' },
      { value: 'MOLDOVAN', label: 'Moldovan' },
      { value: 'MONACAN', label: 'Monacan' },
      { value: 'MONGOLIAN', label: 'Mongolian' },
      { value: 'MOROCCAN', label: 'Moroccan' },
      { value: 'MOSOTHO', label: 'Mosotho' },
      { value: 'MOTSWANA', label: 'Motswana' },
      { value: 'MOZAMBICAN', label: 'Mozambican' },
      { value: 'NAMIBIAN', label: 'Namibian' },
      { value: 'NAURUAN', label: 'Nauruan' },
      { value: 'NEPALESE', label: 'Nepalese' },
      { value: 'NEW_ZEALANDER', label: 'New Zealander' },
      { value: 'NICARAGUAN', label: 'Nicaraguan' },
      { value: 'NIGERIAN', label: 'Nigerian' },
      { value: 'NIGERIEN', label: 'Nigerien' },
      { value: 'NORTH_KOREAN', label: 'North Korean' },
      { value: 'NORTHERN_IRISH', label: 'Northern Irish' },
      { value: 'NORWEGIAN', label: 'Norwegian' },
      { value: 'OMANI', label: 'Omani' },
      { value: 'PAKISTANI', label: 'Pakistani' },
      { value: 'PALAUAN', label: 'Palauan' },
      { value: 'PANAMANIAN', label: 'Panamanian' },
      { value: 'PAPUA_NEW_GUINEAN', label: 'Papua new Guinean' },
      { value: 'PARAGUAYAN', label: 'Paraguayan' },
      { value: 'PERUVIAN', label: 'Peruvian' },
      { value: 'POLISH', label: 'Polish' },
      { value: 'PORTUGUESE', label: 'Portuguese' },
      { value: 'QATARI', label: 'Qatari' },
      { value: 'ROMANIAN', label: 'Romanian' },
      { value: 'RUSSIAN', label: 'Russian' },
      { value: 'RWANDAN', label: 'Rwandan' },
      { value: 'SAINT_LUCIAN', label: 'Saint Lucian' },
      { value: 'SALVADORAN', label: 'Salvadoran' },
      { value: 'SAMOAN', label: 'Samoan' },
      { value: 'SAN_MARINESE', label: 'San Marinese' },
      { value: 'SAO_TOMEAN', label: 'Sao Tomean' },
      { value: 'SAUDI', label: 'Saudi' },
      { value: 'SCOTTISH', label: 'Scottish' },
      { value: 'SENEGALESE', label: 'Senegalese' },
      { value: 'SERBIAN', label: 'Serbian' },
      { value: 'SEYCHELLOIS', label: 'Seychellois' },
      { value: 'SIERRA_LEONEAN', label: 'Sierra Leonean' },
      { value: 'SINGAPOREAN', label: 'Singaporean' },
      { value: 'SLOVAKIAN', label: 'Slovakian' },
      { value: 'SLOVENIAN', label: 'Slovenian' },
      { value: 'SOLOMON_ISLANDER', label: 'Solomon Islander' },
      { value: 'SOMALI', label: 'Somali' },
      { value: 'SOUTH_AFRICAN', label: 'South African' },
      { value: 'SOUTH_KOREAN', label: 'South Korean' },
      { value: 'SPANISH', label: 'Spanish' },
      { value: 'SRI_LANKAN', label: 'Sri Lankan' },
      { value: 'SUDANESE', label: 'Sudanese' },
      { value: 'SURINAMER', label: 'Surinamer' },
      { value: 'SWAZI', label: 'Swazi' },
      { value: 'SWEDISH', label: 'Swedish' },
      { value: 'SWISS', label: 'Swiss' },
      { value: 'SYRIAN', label: 'Syrian' },
      { value: 'TAIWANESE', label: 'Taiwanese' },
      { value: 'TAJIK', label: 'Tajik' },
      { value: 'TANZANIAN', label: 'Tanzanian' },
      { value: 'THAI', label: 'Thai' },
      { value: 'TOGOLESE', label: 'Togolese' },
      { value: 'TONGAN', label: 'Tongan' },
      { value: 'TRINIDADIAN', label: 'Trinidadian' },
      { value: 'TUNISIAN', label: 'Tunisian' },
      { value: 'TURKISH', label: 'Turkish' },
      { value: 'TUVALUAN', label: 'Tuvaluan' },
      { value: 'UGANDAN', label: 'Ugandan' },
      { value: 'UKRAINIAN', label: 'Ukrainian' },
      { value: 'URUGUAYAN', label: 'Uruguayan' },
      { value: 'UZBEKISTANI', label: 'Uzbekistani' },
      { value: 'VENEZUELAN', label: 'Venezuelan' },
      { value: 'VIETNAMESE', label: 'Vietnamese' },
      { value: 'WELSH', label: 'Welsh' },
      { value: 'YEMENITE', label: 'Yemenite' },
      { value: 'ZAMBIAN', label: 'Zambian' },
      { value: 'ZIMBABWEAN', label: 'Zimbabwean' }
    ];

    bindAll(this, [
      'handleSaveButtonClick'
    ]);
  }

  render() {
    return <form className="form-horizontal">
      <Grid>
        <Row>
          <Col xsOffset={2} xs={10}>
            <h3>Personal Details</h3>
          </Col>

          <Col xs={12}>
            <Input type="text" label="Name" id="name" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
            <Input type="text" label="Surname" id="surname" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
            <pre>EMAIL</pre>
            <pre>PHONENUMBER</pre>
            <pre>GITHUB</pre>
            <pre>LINKEDIN</pre>
            <Input type="date" label="Date of Birth" id="dateOfBirth" labelClassName="col-xs-2" wrapperClassName="col-xs-10" />
            {
              // <DateTimeInput
              //   dateTime={moment().format('YY/MM/DD')}
              //   format='YY/MM/DD'
              //   inputFormat='YY/MM/DD'
              //   label="Date of birth"
              //   mode="date"
              //   showToday={true}
              //   id="dateOfBirth"
              // />
            }
            <Input type="select" label="Country of Birth" id="countryOfBirth" labelClassName="col-xs-2" wrapperClassName="col-xs-10">
              { map(this.countries, (country) => <option value={country.value}>{country.label}</option>) }
            </Input>

            {
              // <Select id="countryOfBirth" name="countryOfBirth" options={this.countries} />
            }
            <Input
              type="text"
              label="Current residence"
              labelClassName="col-xs-2"
              wrapperClassName="col-xs-10"
              id="currentResidence"
            />
          </Col>

          <Col xsOffset={2} xs={10}>
            <h3>Education</h3>
          </Col>
          <Col xs={12}>
            <Education ref={(c) => this.educationComponent = c}/>
          </Col>

          <Col xsOffset={2} xs={10}>
            <h3>Certificates</h3>
            <pre>OVERAL VERANDEREN</pre>
          </Col>
          <Col xs={12}>
            <Courses ref={(c) => this.coursesComponent = c}/>
          </Col>

          <Col xsOffset={2} xs={10}>
            <h3>Working Experience</h3>
          </Col>
          <Col xs={12}>
            <Experience ref={(c) => this.experienceComponent = c}/>
          </Col>
        </Row>

        <Row
          style={{
            marginTop: 25
          }}
        >
          <Col xsOffset={2} xs={10}>
            <ButtonGroup>
              <Button type="reset" onClick={this.handleSaveButtonClick} bsStyle="default">Reset</Button>
              <Button type="submit" onClick={this.handleSaveButtonClick} bsStyle="primary">Save</Button>
            </ButtonGroup>
          </Col>
        </Row>
      </Grid>
    </form>;
  }

  handleSaveButtonClick(event) {
    const button = event.target;

    const name = document.querySelector('#name').value;
    const surname = document.querySelector('#surname').value;
    const dateOfBirth = document.querySelector('#dateOfBirth').value;
    const countryOfBirth = document.querySelector('#countryOfBirth').value;
    const currentResidence = document.querySelector('#currentResidence').value;

    const courses = this.coursesComponent.state.entries;
    const education = this.educationComponent.state.entries;
    const experience = this.experienceComponent.state.entries;

    const data = {
      name,
      surname,
      dateOfBirth,
      countryOfBirth,
      currentResidence,
      courses,
      education,
      experience
    };

    debugger;

    qwest
    .post('http://localhost:3000', data)
      .then((xhr, response) => {
        debugger;
      })
      .catch((xhr, response, error) => {
        debugger;
      })
  }
}

Employee.displayName = 'Empoyee';

export default Employee;
