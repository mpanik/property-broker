'use strict';

angular.module('propertyBrokerApp.controllers').
    controller('MainCtrl', function ($scope, uiGmapGoogleMapApi, PropertyService) {

        $scope.markers = [];

        uiGmapGoogleMapApi.then(function() {
            $scope.map = { center: { latitude: 49.2044255, longitude: 16.6325489 }, zoom: 12 };
        });

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

