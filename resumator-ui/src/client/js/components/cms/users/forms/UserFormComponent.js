import newforms from 'newforms';

export default newforms.Form.extend({
  id: newforms.CharField({
    required: false,
    hidden: true,
    maxLength: 24,
    widgetAttrs: {
      disabled: true
    }
  }),

  username: newforms.CharField({
    required: true,
    maxLength: 100
  }),

  email: newforms.CharField({
    required: true,
    maxLength: 100
  })
});
