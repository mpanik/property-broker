'use strict';

/**
 * @ngdoc function
 * @name propertyBrokerApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the propertyBrokerApp
 */
angular.module('propertyBrokerApp')
  .controller('MainCtrl', function ($scope, uiGmapGoogleMapApi) {

    uiGmapGoogleMapApi.then(function() {
      $scope.map = { center: { latitude: 49.2044255, longitude: 16.6325489 }, zoom: 12 };
    });

  });
