'use strict';

angular.module('propertyBrokerApp.directives')
    .directive('search', function () {
        return {
            templateUrl: 'scripts/directives/search-directive/search-directive.html',
            restrict: 'E',
            scope: {
                data: '='
            },
            controller: ['$scope', function ($scope) {

                $scope.$watch('data', function (newValue) {
                    $scope.data = newValue;
                }, true);

                $scope.propertySelected = function(selected) {
                    console.log(selected);
                    //$scope.markers = buildMarkersArray($scope.propertySelected);
                    //TODO: better info (not alert) + show on map (with different colored marker), when unselected remove marker from map
                };

                function buildMarkersArray(properties) {
                    var markers = [];
                    var id = 0;
                    for(var i = 0; i < properties.length; i++) {
                        var latitude = properties[i].x;
                        var longitude = properties[i].y;
                        var marker = {id: id, coords: {latitude: latitude, longitude: longitude}};
                        id++;
                        markers.push(marker);
                    }
                    return markers;
                }

            }]
        };
    });
