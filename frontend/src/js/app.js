// Configuration
var apiHostname = 'localhost';
var apiPort = 80;

// Models
var EducationModel = Backbone.Model.extend({
});

var CourseModel = Backbone.Model.extend({
});

var EmployeeModel = Backbone.Model.extend({
  defaults: {
    name: '',
    surname: '',
    yearOfBirth: '',
    nationality: '',
    currentResidence: '',

    courses: [],
    education: [],
    experience: []
  }
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

    $("#inputYearOfBirth").datepicker({
    }).on('changeDate', function(e){
      $(this).datepicker('hide');
    });

    $("#inputNationality").selectpicker({
    });

    $('#resetButton').on('click', function(event) {
      event.preventDefault();

      window.location = '#employee'
    });
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
    var pageView = new EmployeeNewPageView();

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
  console.log('DOM ready');

  applicationView.render();
  navigationView.render();

  Backbone.history.start();

  console.log('Application started');
});
