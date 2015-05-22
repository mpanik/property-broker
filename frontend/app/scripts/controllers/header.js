'use strict';
angular.module('propertyBrokerApp.controllers').
    controller('HeaderController', function ($scope, $location) {

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

    });
