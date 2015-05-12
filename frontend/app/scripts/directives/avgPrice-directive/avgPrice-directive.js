'use strict';

angular.module('propertyBrokerApp.directives')
    .directive('avgPrice', function () {
        return {
            templateUrl: 'scripts/directives/avgPrice-directive/avgPrice-directive.html',
            restrict: 'E',
            scope: {
                values: '='
            },
            controller: ['$scope', function ($scope) {

                $scope.$watch('values', function (newValue) {
                    $scope.values = newValue;
                }, true);

                //TODO: logic here

            }]
        };
    });
