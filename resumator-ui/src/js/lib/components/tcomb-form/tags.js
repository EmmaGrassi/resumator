import React from 'react';
import tcombForm from 'tcomb-form';
import TagsInput from 'react-tagsinput';

const template = tcombForm.form.Form.templates.textbox.clone({
  // override just the textbox rendering...
  renderTextbox: (locals) => <TagsInput value={locals.value} onChange={(tags) => locals.onChange(tags)} />
});

class TagsComponent extends tcombForm.form.Component {
  static transformer = {
    format: (value) => value || [],
    parse: (value) => value
  };

  getTemplate() {
    return template;
  }
}

export default TagsComponent;
