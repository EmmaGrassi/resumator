import moment from 'moment';
import React from 'react';
import {
  Col,
  Grid,
  Input,
  Row
} from 'react-bootstrap';
import DateTimeInput from 'react-bootstrap-datetimepicker';

class Experience extends React.Component {
  constructor(options) {
    super(options);
  }

  render() {
    return <div>
      <Input type="text" label="Company Name" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
      <Input type="text" label="Title" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
      <Input type="text" label="Location" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
      <Input type="text" label="Short Description" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>

      <Input type="text" label="Roles" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
      <Input type="text" label="Technologies" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>
      <Input type="text" label="Methodologies" labelClassName="col-xs-2" wrapperClassName="col-xs-10"/>

      <Row>
        <Col xs={10}>
          <DateTimeInput
            dateTime={moment().format('YY/MM/DD')}
            format='YY/MM/DD'
            inputFormat='YY/MM/DD'
            showToday="true"
            mode="days"
            label="Date of birth"
          />
        </Col>
      </Row>

      <Col xs={2}>
        {/* TODO: Label */}
      </Col>
      <Col xs={10}>
        <DateTimeInput
          dateTime={moment().format('YY/MM/DD')}
          format='YY/MM/DD'
          inputFormat='YY/MM/DD'
          showToday="true"
          mode="days"
          label="Date of birth"
        />
      </Col>
    </div>;
  }
}

Experience.displayName = 'Experience';

export default Experience;
