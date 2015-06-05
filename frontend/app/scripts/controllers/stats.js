'use strict';

angular.module('propertyBrokerApp.controllers').
    controller('StatsCtrl', ['$scope', 'PropertyService', 'lodash',
        function ($scope, PropertyService, _) {

            PropertyService.findAll(
                function (data) {
                    $scope.properties = data;
                    $scope.districtsStats = calculateDistrictsStats($scope.properties);
                }
            );

            function divideValues(item1, item2) {
                return Math.round(parseInt(item1) / parseInt(item2));
            }

            function parseIntAndGetMaxPrice(item1, item2) {
                return Math.max(parseInt(item1.price), parseInt(item2.price)) === parseInt(item1.price) ? item1 : item2;
            }

            function parseIntAndGetMinPrice(item1, item2) {
                return Math.min(parseInt(item1.price), parseInt(item2.price)) === parseInt(item1.price) ? item1 : item2;
            }

            function extractDistrictsStatsFrom(properties) {
                var statsByDistrict = [];

                function updateAndSumStats(objectToBeUpdated, property) {
                    objectToBeUpdated.sumPrices += property.price;
                    objectToBeUpdated.sumArea += property.area;
                    objectToBeUpdated.count++;
                    objectToBeUpdated.mostExpensive = parseIntAndGetMaxPrice(objectToBeUpdated.mostExpensive, property);
                    objectToBeUpdated.cheapest = parseIntAndGetMinPrice(objectToBeUpdated.cheapest, property);
                }

                function initStreetsArray(property) {
                    if (statsByDistrict[property.address.district].streets === undefined) {
                        statsByDistrict[property.address.district].streets = [];
                    }
                    statsByDistrict[property.address.district].streets[property.address.street] = initStats(property);
                }

                function initStats(property) {
                    return {
                        sumPrices: property.price,
                        sumArea: property.area,
                        count: 1,
                        mostExpensive: property,
                        cheapest: property
                    };
                }

                function updateStreetStats(property) {
                    if (_.has(statsByDistrict[property.address.district].streets, property.address.street)) {
                        updateAndSumStats(statsByDistrict[property.address.district].streets[property.address.street], property);
                    } else {
                        initStreetsArray(property);
                    }
                }

                _.forEach(properties, function (property) {
                    if (_.has(statsByDistrict, property.address.district)) {
                        updateAndSumStats(statsByDistrict[property.address.district], property);
                        updateStreetStats(property);
                    } else {
                        statsByDistrict[property.address.district] = initStats(property);
                        initStreetsArray(property);
                    }
                });

                return statsByDistrict;
            }

            function calculateDistrictsStats(properties) {
                var districts = extractDistrictsStatsFrom(properties);
                var districtsStats = {};

                function makeStats(object) {
                    return {
                        avgPriceMSquare: divideValues(object.sumPrices, object.sumArea),
                        count: object.count,
                        avgPrice: divideValues(object.sumPrices, object.count),
                        mostExpensive: object.mostExpensive,
                        cheapest: object.cheapest
                    };
                }

                function initStreetStatsArrayIfNecessary(districtName) {
                    if (districtsStats[districtName].streetsStats === undefined) {
                        districtsStats[districtName].streetsStats = {};
                    }
                }

                function calculateStreetsStatsForDistrict(districtStats, districtName) {
                    _.forIn(districtStats.streets, function (streetStats, streetName) {
                        districtsStats[districtName].streetsStats[streetName] = makeStats(streetStats);
                    });
                }

                _.forIn(districts, function (districtStats, districtName) {
                    districtsStats[districtName] = makeStats(districtStats);
                    initStreetStatsArrayIfNecessary(districtName);
                    calculateStreetsStatsForDistrict(districtStats, districtName);
                });

                return districtsStats;
            }

        }]
);
