module.exports = (plop) => {
  // Component test
  plop.setGenerator('component-test', {
    description: 'Generates a new component test',
    prompts: [{
      type: 'input',
      name: 'component',
      message: 'Which component do you want to test?',
      validate: (value) => {
        if ((/.+/).test(value)) { return true; }
        return 'component name is required';
      },
    },
    {
      type: 'input',
      name: 'path',
      message: 'What is the path to this component?',
      validate: (value) => {
        if ((/.+/).test(value)) { return true; }
        return 'component path is required';
      },
    }],
    actions: [{
      type: 'add',
      path: 'test/{{pascalCase component}}.js',
      templateFile: '.ploptemplates/component-test.template',
    }],
  });

  // Connected component
  plop.setGenerator('connected', {
    description: 'Generates a new connected component',
    prompts: [{
      type: 'input',
      name: 'component',
      message: 'What\'s the name?',
      validate: (value) => {
        if ((/.+/).test(value)) { return true; }
        return 'component name is required';
      },
    }],
    actions: [{
      type: 'add',
      path: 'src/js/components/public/{{pascalCase component}}.js',
      templateFile: '.ploptemplates/connected-component.template',
    }],
  });

  // Classic component
  plop.setGenerator('classic', {
    description: 'Generates a new classic component',
    prompts: [{
      type: 'input',
      name: 'component',
      message: 'What\'s the name?',
      validate: (value) => {
        if ((/.+/).test(value)) { return true; }
        return 'component name is required';
      },
    }],
    actions: [{
      type: 'add',
      path: 'src/js/components/public/{{pascalCase component}}.js',
      templateFile: '.ploptemplates/classic-component.template',
    }],
  });

  // Stateless component
  plop.setGenerator('stateless', {
    description: 'Generates a new stateless component',
    prompts: [{
      type: 'input',
      name: 'component',
      message: 'What\'s the name?',
      validate: (value) => {
        if ((/.+/).test(value)) { return true; }
        return 'component name is required';
      },
    }],
    actions: [{
      type: 'add',
      path: 'src/js/components/public/{{pascalCase component}}.js',
      templateFile: '.ploptemplates/stateless-component.template',
    }],
  });

};
