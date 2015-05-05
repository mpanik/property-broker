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
    'ngRoute',
    'uiGmapgoogle-maps'
  ])
  .config(function ($routeProvider, uiGmapGoogleMapApiProvider) {
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
  });
