'use strict';

angular.module('propertyBrokerApp.directives')
    .directive('search', function () {
        return {
            templateUrl: 'scripts/directives/search-directive/search-directive.html',
            restrict: 'E',
            scope: {
                values: '='
            },
            controller: ['$scope', function ($scope) {

                $scope.propertySelected = function(selected) {
                    console.log(selected);
                    //TODO: better info (not alert) + show on map (with different colored marker), when unselected remove marker from map
                };

                $scope.$watch('values', function (newValue) {
                    $scope.values = newValue;
                }, true);

            }]
        };
    });
