import Loader from 'react-loader';
import React from 'react';
import { bindAll, map } from 'lodash';
import { connect } from 'react-redux';
import { pushPath } from 'redux-simple-router';
import Mustache from 'mustache';

import ace from 'brace';
import 'brace/mode/html';
import 'brace/theme/chaos';

import templateString from '../../../data/template_1';
import actions from '../../../actions';

function mapStateToProps(state) {
  return {
    data: state.user.profile.toJS().item,
  };
}

function mapDispatchToProps(dispatch) {
  return {
    fetchEmployee: (email) => dispatch(actions.employees.show(email)),
  };
}

class Editor extends React.Component {

  constructor(props) {
    super(props);
    this.props = props;
    this.state = {
      windowWidth: window.innerWidth,
      aceTemplate: templateString,
    };
  }

  componentWillMount() {
    // this.props.fetchEmployee(this.props.params.userId);
    window.addEventListener('resize', this.handleResize.bind(this));
  }

  componentDidMount() {
    this.editor = ace.edit('template-editor');
    this.editor.getSession().setMode('ace/mode/html');
    this.editor.setTheme('ace/theme/chaos');
    this.editor.setValue(this.state.aceTemplate);
    this.editor.getSession().on('change', this.onAceChange.bind(this));
  }

  onAceChange() {
    this.setState({ aceTemplate: this.editor.getSession().getValue() });
  }

  handleResize() {
    this.setState({ windowWidth: window.innerWidth });
  }

  render() {
    return (
      <div>
        <div
          id="template-editor"
          className="template-editor"
          style={{
            position: 'fixed',
            top: '40px',
            right: 0,
            bottom: 0,
            width: `${this.state.windowWidth * 0.6}px`,
            zIndex: 50,
          }}
        />
        <div
          id="preview"
          dangerouslySetInnerHTML={{ __html: Mustache.render(this.state.aceTemplate, this.props.data) }}
          style={{
            position: 'fixed',
            top: '40px',
            left: 0,
            bottom: 0,
            width: `${this.state.windowWidth * 0.4}px`,
            backgroundColor: '#fff',
            zIndex: 50,
          }}
        />
      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Editor);
