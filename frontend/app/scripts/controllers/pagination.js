'use strict';

angular.module('propertyBrokerApp.controllers').
    controller('PaginationCtrl', function ($scope, PropertyService) {

        $scope.properties = [];
        PropertyService.findAll(
            function(data) {
                $scope.properties = data;
                initializePage($scope.properties);
                $scope.serverError = false;
            },

            function() {
                $scope.serverError = true;
            }
        );


        var initializePage = function(data) {
            $scope.totalItems = data.length;
            $scope.currentPage = 1;
            $scope.itemsPerPage = 20;
            $scope.maxSize = 10;
            $scope.filteredProperties = data.slice(0, $scope.itemsPerPage);

            $scope.setPage = function (pageNo) {
                $scope.currentPage = pageNo;
            };

            $scope.pageChanged = function() {
                var begin = ($scope.currentPage - 1) * $scope.itemsPerPage + 1;
                var end = begin + $scope.itemsPerPage;
                $scope.filteredProperties = data.slice(begin, end);
            };
        };
    });
