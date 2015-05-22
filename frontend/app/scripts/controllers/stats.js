'use strict';
angular.module('propertyBrokerApp.controllers').
    controller('StatsCtrl', function ($scope, PropertyService) {

        PropertyService.findAll(
            function(data) {
                $scope.properties = data;
                $scope.serverError = false;
            },
            function() {
                $scope.serverError = true;
            }
        );

    });
