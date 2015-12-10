import Application from './Application';

import log from 'loglevel';

log.setLevel('debug');

const app = window.app = new Application({
});

app.start();

/*
// Configuration
var apiHostname = 'localhost';
var apiPort = 80;

// Models
var EducationModel = Backbone.Model.extend({
});

var CourseModel = Backbone.Model.extend({
});

var CoursesCollection = Backbone.Collection.extend({
  model: CourseModel
});

var EmployeeModel = Backbone.Model.extend({
});

var EventModel = Backbone.Model.extend({
});

var ExperienceModel = Backbone.Model.extend({
});

// Views
var View = Backbone.View.extend({
  initialize: function(options) {
    this.options = options;
  },

  render: function() {
    this.$el.html(this.template());

    return this;
  }
});

var ApplicationView = View.extend({
  el: '#applicationContainer',
  template: _.template($('script.applicationTemplate').html()),
});

var NavigationView = View.extend({
  el: '#navigationContainer',
  template: _.template($('script.navigationTemplate').html()),

  events: {
    'click nav a': 'handleLinkClick'
  },

  handleLinkClick: function(event) {
    var target = event.target;

    this
      .deselectAllSiblingMenuItemsByLink(target)
      .selectMenuItemByLink(target);
  },

  deselectAllSiblingMenuItemsByLink: function(clickedMenuItem) {
    $(clickedMenuItem)
      .parents('ul')
      .children('li')
      .removeClass('active');

    return this;
  },

  deselectAllSiblingMenuItemsByClass: function(className) {
    $('#navigationContainer nav a.' + className)
      .parents('ul')
      .children('li')
      .removeClass('active');

    return this;
  },

  selectMenuItemByLink: function(clickedMenuItem) {
    $(clickedMenuItem)
      .parents('li')
      .addClass('active');

    return this;
  },

  selectMenuItemByClass: function(className) {
    $('#navigationContainer nav a.' + className)
      .parents('li')
      .addClass('active');

    return this;
  },

  navigateTo: function(className) {
    this
      .deselectAllSiblingMenuItemsByClass(className)
      .selectMenuItemByClass(className);

    return this;
  }
});

var ApplicationIndexPageView = View.extend({
  el: '#contentContainer',
  template: _.template($('script.applicationIndexPageTemplate').html()),
});

var EmployeeIndexPageView = View.extend({
  el: '#contentContainer',
  template: _.template($('script.employeeIndexPageTemplate').html()),
});

var EmployeeSearchView = View.extend({
  // el: '#employeeSearchContainer',
  template: _.template($('script.employeeSearchTemplate').html()),
});

var EmployeeNewPageView = View.extend({
  el: '#contentContainer',
  template: _.template($('script.employeeNewPageTemplate').html()),

  render: function () {
    View.prototype.render.call(this);

    var coursesView = new EmployeeCoursesView({
      collection: this.model.get('courses')
    });

    coursesView.render();

    $("#inputYearOfBirth").datepicker()
      .on('changeDate', function(e){
        $(this).datepicker('hide');
      });

    $("#inputNationality").selectpicker();

    $('#resetButton')
      .on('click', function(event) {
        event.preventDefault();

        window.location = '#employee'
      });
  }
});

var EmployeeCoursesView = View.extend({
  el: '#employeeCoursesContainer',
  template: _.template($('script.employeeCoursesTemplate').html()),

  events: {
    'click .employeeCoursesEntryButton': 'handleEmployeeCoursesEntryButtonClick'
  },

  render: function() {
    var self = this;

    View.prototype.render.call(this);

    this.collection.each(function(model) {
      var view = new EmployeeCoursesEntryView({
        model: model
      });

      view.on('remove', self.handleEmployeeCoursesEntryRemove.bind(self));

      view.render();

      this.$('#employeeCoursesEntriesContainer').append(view.el);
    });
  },

  handleEmployeeCoursesEntryRemove: function(model) {
    debugger;

    this.collection.remove(model);

    this.render();
  },

  handleEmployeeCoursesEntryButtonClick: function(event) {
    event.preventDefault();

    var value = this.$('.employeeCoursesEntryInput').val();

    if (!value || value === '') {
      return;
    }

    var model = new CourseModel({
      value: value
    });

    this.collection.add(model);

    this.render();
  }
});

var EmployeeCoursesEntryView = View.extend({
  // el: '#employeeCoursesEntriesContainer',
  template: _.template($('script.employeeCoursesEntryTemplate').html()),

  events: {
    'click .remove': 'handleRemoveClick'
  },

  render: function() {
    this.$el.html(this.template(this.model.toJSON()));
  },

  handleRemoveClick: function(event) {
    event.preventDefault();

    this.trigger('remove', this.model);
  }
});

// Router
var ApplicationRouter = Backbone.Router.extend({
  routes: {
    '': 'applicationIndex',
    'employee': 'employeeIndex',
    'employee/new': 'employeeNew',
    'employee/:id': 'employeeView',
    'employee/:id/edit': 'employeeEdit',
    'employee/:id/destroy': 'employeeDestroy'
  },

  applicationIndex: function() {
    var pageView = new ApplicationIndexPageView();

    // Select the correct menu item.
    navigationView
      .navigateTo('applicationIndex');

    // Render the page.
    pageView.render();
  },

  employeeIndex: function() {
    var pageView = new EmployeeIndexPageView();
    var searchView = new EmployeeSearchView();

    // Select the correct menu item.
    navigationView
      .navigateTo('employeeIndex');

    // Render the page and the search.
    pageView.render();
    searchView.render();

    // Put the search into the page.
    $('#employeeSearchContainer').html(searchView.$el);
  },

  employeeNew: function() {
    var employeeModel = new EmployeeModel();
    var coursesCollection = new CoursesCollection;

    employeeModel.set('courses', coursesCollection);

    var pageView = new EmployeeNewPageView({
      model: employeeModel
    });

    // Select the correct menu item.
    navigationView
      .navigateTo('employeeIndex');

    pageView.render();
  },

  employeeView: function(id) {
    debugger;
  },

  employeeEdit: function(id) {
    debugger;
  },

  employeeDestroy: function(id) {
    debugger;
  }
});

// Initialize the app.
var applicationView = new ApplicationView();
var navigationView = new NavigationView();
var router = new ApplicationRouter();

// When the DOM is ready
$(document).ready(function() {
  applicationView.render();
  navigationView.render();

  Backbone.history.start();
});
*/
