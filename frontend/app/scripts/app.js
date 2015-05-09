'use strict';

/**
 * @ngdoc overview
 * @name propertyBrokerApp
 * @description
 * # propertyBrokerApp
 *
 * Main module of the application.
 */
var app = angular
  .module('propertyBrokerApp', [
    'ngAnimate',
    'ngResource',
    'ngRoute',
    'uiGmapgoogle-maps'
  ]);

  app.config(function ($routeProvider, uiGmapGoogleMapApiProvider, $httpProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });

    uiGmapGoogleMapApiProvider.configure({
      v: '3.17',
      libraries: 'weather,geometry,visualization'
    });

   /* $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];*/

  });

  app.factory('properties', ['$resource', function($resource) {

    return $resource('localhost:8080/pbroker/rest/properties');

  }]);

