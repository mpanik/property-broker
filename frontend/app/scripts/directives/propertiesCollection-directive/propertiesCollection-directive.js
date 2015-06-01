'use strict';

angular.module('propertyBrokerApp.directives')
    .directive('propertiesCollection', function () {
        return {
            templateUrl: 'scripts/directives/propertiesCollection-directive/propertiesCollection-directive.html',
            restrict: 'E',
            scope: {
                data: '='
            },
            controller: ['$scope', '$filter', function ($scope, $filter) {

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
                    for(var index = 0; index < $scope.filteredProperties.length; index++) {
                        var marker = {
                            id: index,
                            coords: {
                                latitude: $scope.filteredProperties[index].coords.x,
                                longitude: $scope.filteredProperties[index].coords.y
                            }
                        };
                        $scope.$emit('marker-added', marker);
                    }
                };

                $scope.propertySelected  = function(selected){
                    $scope.marker = {
                        selected: selected,
                        coords: {
                            latitude: selected.coords.x,
                            longitude: selected.coords.y
                        },
                        options: {
                            icon:{url:"http://maps.google.com/mapfiles/ms/icons/yellow-dot.png"}
                        }
                    };
                    $scope.$emit('marker-updated', $scope.marker);
                };

            }]
        };
    });
