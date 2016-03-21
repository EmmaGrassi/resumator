import React from 'react';
import { pushPath } from 'redux-simple-router';
import modelUserForImport from '../../../helpers/modelUserForImport';

import {
  Button,
  Modal,
} from 'react-bootstrap';

const fields = [
  'first-name',
  'last-name',
  'headline',
  'location',
  'summary',
  'specialties',
  'picture-url',
  'picture-urls::(original)',
  'positions:(title,summary,start-date,end-date,is-current)',
  'educations:(school-name,field-of-study,start-date,end-date,degree,activities,notes)',
];

const profileURL = `/people/~:(${fields.join(',')})`;


class ImportModal extends React.Component {

  constructor(props) {
    super(props);
    this.props = props;
    this.state = {
      loggedIn: false,
      currentUser: null,
      hasImported: false,
    };
  }

  componentWillMount() {
  }

  onLogin() {
    window.IN.User.authorize(this.onLoggedIn.bind(this));
  }

  onLoggedIn() {
    window.IN.API.Raw('/people/~').result((data) => {
      this.setState({ loggedIn: true, currentUser: data });
    });
  }

  onStartImport() {
    const really = confirm('Are you sure? This action will override any existing data.');
    if (really) alert('Import done');
  }

  onUserDataReceived(data) {
    const prettyUser = modelUserForImport(data);
    this.setState({ currentUser: prettyUser, hasImported: true });
  }

  getMyInfo() {
    window.IN.API.Raw(profileURL).result(this.onUserDataReceived.bind(this));
  }

  renderReadyToImport() {
    const values = Object.keys(this.state.currentUser).map(k => {
      if (typeof this.state.currentUser[k] === 'string') {
        return (<div key={k}>{`${k}: ${this.state.currentUser[k]}`}</div>);
      }
      return (<div key={k}>{`${k}: ${JSON.stringify(this.state.currentUser[k])}`}</div>);
    });
    return (
      <Modal.Body>
        <div>
          {values}
        </div>
      </Modal.Body>);
  }

  renderLogin() {
    return (
      <Modal.Body>
        <a href="#" onClick={this.onLogin.bind(this)}>
          <img src="/images/linkedin-button.png" />
        </a>
      </Modal.Body>
    );
  }

  render() {
    const modalBody = this.state.loggedIn && this.state.currentUser ?
      this.renderReadyToImport() : this.renderLogin();
    return (
      <div className="static-modal">
        <Modal.Dialog>
          <Modal.Header>
            <Modal.Title>Import from LinkedIn</Modal.Title>
          </Modal.Header>

          {modalBody}

          <Modal.Footer>
            <Button bsStyle="danger"
              onClick={this.props.onClose}
            >Cancel</Button>
            {this.state.loggedIn && !this.state.hasImported ?
            <Button
              bsStyle="primary"
              onClick={this.getMyInfo.bind(this)}
            >Get my info</Button> : null}
            {this.state.hasImported ?
              <Button
                bsStyle="primary"
                onClick={this.onStartImport.bind(this)}
              >Import</Button> : null}
          </Modal.Footer>
          </Modal.Dialog>
        </div>);
  }
}

export default ImportModal;
