'use strict';

angular.module('propertyBrokerApp.controllers').
    controller('StatsCtrl', ['$scope', 'growl', 'PropertyService',
        function ($scope, growl, PropertyService) {

            PropertyService.findAll(
                function(data) {
                    $scope.properties = data;
                },
                function() {
                    growl.error('Failed to connect to server. Please try again later.');
                }
            );

        }]
);
