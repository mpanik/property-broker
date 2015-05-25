'use strict';

angular.module('propertyBrokerApp.controllers').
    controller('MainCtrl', ['$scope', 'growl', 'uiGmapGoogleMapApi', 'PropertyService',
        function ($scope, growl, uiGmapGoogleMapApi, PropertyService) {

            $scope.markers = [];

            uiGmapGoogleMapApi.then(function() {
                $scope.map = { center: { latitude: 49.2044255, longitude: 16.6325489 }, zoom: 12 };
            });

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
