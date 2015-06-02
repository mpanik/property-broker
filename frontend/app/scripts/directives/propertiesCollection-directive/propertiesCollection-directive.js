'use strict';

angular.module('propertyBrokerApp.directives')
    .directive('propertiesCollection', function () {
        return {
            templateUrl: 'scripts/directives/propertiesCollection-directive/propertiesCollection-directive.html',
            restrict: 'E',
            scope: {
                data: '='
            },
            controller: ['$scope', '$filter', 'lodash', function ($scope, $filter, _) {

                $scope.$watch('data', function (newValue) {
                    $scope.data = newValue;
                }, false);

                $scope.filteredProperties = [];
                $scope.currentPage = 1;
                $scope.itemsPerPage = 5;
                $scope.maxSize = 10;

                $scope.$watch('query', function (query) {
                    $scope.filteredData = $filter('filter')($scope.data, query);
                    $scope.totalItems = $scope.filteredData.length;
                    $scope.pageChanged();
                }, false);

                $scope.setPage = function (pageNo) {
                    $scope.currentPage = pageNo;
                };

                $scope.pageChanged = function() {
                    var begin = ($scope.currentPage - 1) * $scope.itemsPerPage;
                    var end = begin + $scope.itemsPerPage;
                    $scope.filteredProperties = $scope.filteredData.slice(begin, end);

                    $scope.$emit('markers-updated', makeMarkers());
                };

                function makeMarkers() {
                    var markers = [];

                    _.forEach($scope.filteredProperties, function(item) {
                        markers.push(makeMarker(item));
                    });
                    return markers;
                }

                function makeMarker(model) {
                    return {
                        id: model.id,
                        coords: {
                            latitude: model.coords.x,
                            longitude: model.coords.y
                        }
                    }
                }

                $scope.propertySelected  = function(selected){
                    var marker = makeMarker(selected);
                    marker.options = {
                        icon:{url:"http://maps.google.com/mapfiles/ms/icons/yellow-dot.png"}
                    };
                    $scope.$emit('marker-updated', marker);
                };

            }]
        };
    });
