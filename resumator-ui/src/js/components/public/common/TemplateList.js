import Loader from 'react-loader';
import React from 'react';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';
import TemplateView from './TemplateView';
import faker from 'faker';
import parameterize from 'parameterize';

import {
  Button,
} from 'react-bootstrap';

import actions from '../../../actions';

function getTemplates() {
  const templates = [];
  for (let i = 0; i < 6; i++) {
    templates.push({
      name: faker.commerce.productName(),
      lastUpdated: faker.date.past(),
      image: 'https://cdn4.iconfinder.com/data/icons/office-and-business-vol-3/32/611_letterpad_writingpad_employee_man_resume_biodata-256.png',
    });
  }
  return templates;
}

function mapStateToProps(state) {
  return {
    user: state.user.profile.toJS().item,
  };
}

function mapDispatchToProps(dispatch) {
  return {
    fetchEmployee: (email) => dispatch(actions.employees.show(email)),
    navigateToTemplate: (id) => dispatch(pushPath(`/templates/edit/${id}`)),
  };
}

class TemplateList extends React.Component {

  constructor(props) {
    super(props);
    this.props = props;
    this.state = {
    };
  }

  componentWillMount() {
  }

  componentDidMount() {
  }

  onTemplateClicked(template, e) {
    console.log('Template picked', template);
    this.props.navigateToTemplate(parameterize(template.name));
  }

  render() {
    const templates = getTemplates()
      .sort((a, b) => a.lastUpdated < b.lastUpdated)
      .map((t, i) => <TemplateView key={i} item={t} onClick={this.onTemplateClicked.bind(this, t)} />);
    return (
      <div className="container">
        <div className="row">
          <Button
            bsStyle="success"
            href="/#/templates/new"
            style={{ float: 'right' }}
          >New Template</Button>
          <h1 style={{ float: 'left' }}>Templates</h1>
        </div>
        <hr />
        <div className="list-container">
          {templates}
        </div>
      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(TemplateList);
