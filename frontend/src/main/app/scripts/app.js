'use strict';

/**
 * @ngdoc overview
 * @name propertyBrokerApp
 * @description
 * # propertyBrokerApp
 *
 * Main module of the application.
 */
angular
  .module('propertyBrokerApp', [
    'ngAnimate',
    'ngResource',
    'ngRoute'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
