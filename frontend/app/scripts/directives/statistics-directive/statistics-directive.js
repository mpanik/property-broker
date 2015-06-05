'use strict';

angular.module('propertyBrokerApp.directives')
    .directive('statistics', function () {
        return {
            templateUrl: 'scripts/directives/statistics-directive/statistics-directive.html',
            restrict: 'E',
            scope: {
                districtsStats: '=data'
            },
            controller: ['$scope', 'lodash', function ($scope, _) {

                $scope.$watch('districtsStats', function (newValue) {
                    $scope.districtsStats = newValue;
                }, false);

                var rowsByDistricts = {};

                $scope.isExpanded = function(key) {
                    return _.has(rowsByDistricts, key) && rowsByDistricts[key].expanded;
                };

                $scope.expandRow = function(key) {
                    function collapse() {
                        rowsByDistricts[key].expanded = false;
                    }

                    function expand() {
                        if (rowsByDistricts[key] === undefined)
                            rowsByDistricts[key] = {};
                        rowsByDistricts[key].expanded = true;
                    }

                    $scope.isExpanded(key) ? collapse() : expand();
                };

            }]
        };
    });
