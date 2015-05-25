'use strict';

angular.module('propertyBrokerApp.directives')
    .directive('navbar', function () {
        return {
            templateUrl: 'scripts/directives/navbar-directive/navbar-directive.html',
            restrict: 'E',
            controller: ['$scope', '$state', function ($scope, $state) {

                $scope.$state = $state;

            }]
        };
    });
