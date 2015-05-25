'use strict';

angular.module('propertyBrokerApp.directives')
    .directive('search', function () {
        return {
            templateUrl: 'scripts/directives/search-directive/search-directive.html',
            restrict: 'E',
            scope: {
                query: '='
            },
            controller: ['$scope', function ($scope) {

            }]
        };
    });
