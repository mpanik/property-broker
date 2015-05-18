'use strict';

angular.module('propertyBrokerApp.directives')
    .directive('statistics', function () {
        return {
            templateUrl: 'scripts/directives/statistics-directive/statistics-directive.html',
            restrict: 'E',
            scope: {
                data: '='
            },
            controller: ['$scope', function ($scope) {

                $scope.$watch('data', function (newValue) {
                    $scope.data = newValue;
                }, true);

                function getMinMaxPrice(boolMin, propertyA, propertyB) {
                    var price;
                    if (boolMin === true) {
                        price = Math.min(parseInt(propertyA.price), parseInt(propertyB.price));
                    } else {
                        price = Math.max(parseInt(propertyA.price), parseInt(propertyB.price));
                    }

                    if (parseInt(propertyB.price) === parseInt(price)) {
                        return propertyB;
                    } else {
                        return propertyA;
                    }
                }

                function sumValues(valueA,valueB){
                    return  parseInt(valueA) + parseInt(valueB);
                }

                function divideValues(valueA,valueB){
                    return  Math.round( parseInt(valueA) / parseInt(valueB));
                }



                function getDistrictStats(properties) {
                    var pricesInDistricts = [];

                    properties.forEach(function (property) {
                        var isDistrictListed = false;

                        pricesInDistricts.forEach(function (propByDistrict) {
                            if (property.address.district === propByDistrict.district && !isDistrictListed) {
                                propByDistrict.sumPrices =sumValues(propByDistrict.sumPrices,property.price);
                                propByDistrict.sumArea = sumValues(propByDistrict.sumArea,property.area);
                                propByDistrict.count = sumValues(propByDistrict.count,1);
                                propByDistrict.mostExpensive = getMinMaxPrice(false, propByDistrict.mostExpensive, property);
                                propByDistrict.cheapest = getMinMaxPrice(true, propByDistrict.cheapest, property);
                                propByDistrict.properties.push(property);
                                isDistrictListed = true;
                                console.log(propByDistrict.Cheapest);
                            }
                        });
                        if (!isDistrictListed) {
                            pricesInDistricts.push({
                                district: property.address.district,
                                sumPrices: parseInt(property.price),
                                sumArea: parseInt(property.area),
                                count: 1,
                                mostExpensive: property,
                                cheapest: property,
                                properties: [property]
                            });
                        }


                    });
                    return pricesInDistricts;
                }


                function calculateDistrictsStats() {

                    var pricesInDistricts = getDistrictStats($scope.data);
                    var avgPricesInDistricts = [];

                    pricesInDistricts.forEach(function (propByDistrict) {
                        avgPricesInDistricts.push(
                            {
                                district: propByDistrict.district,
                                avgPriceMSquare: divideValues(propByDistrict.sumPrices, propByDistrict.sumArea),
                                count: propByDistrict.count,
                                avgPrice: divideValues(propByDistrict.sumPrices,propByDistrict.count),
                                mostExpensive: propByDistrict.mostExpensive,
                                cheapest: propByDistrict.cheapest
                            }
                        );
                    });
                    return avgPricesInDistricts;
                }

                function getStreetStats() {

                    var valueDistrictStreet = getDistrictStats($scope.data);
                    var pricesAllDistricts = [];

                    valueDistrictStreet.forEach(function (district) {

                        var pricesAllStreets = [];

                        district.properties.forEach(function (propertyStreet) {

                            var isStreetListed = false;

                            pricesAllStreets.forEach(function (propByDistrict) {
                                if (propertyStreet.address.street === propByDistrict.street && !isStreetListed) {
                                    propByDistrict.sumPrices = sumValues(propByDistrict.sumPrices,propertyStreet.price);
                                    propByDistrict.sumArea = sumValues(propByDistrict.sumArea,propertyStreet.area);
                                    propByDistrict.count =sumValues(propByDistrict.count,1);
                                    propByDistrict.mostExpensive = getMinMaxPrice(false, propByDistrict.mostExpensive, propertyStreet);
                                    propByDistrict.cheapest = getMinMaxPrice(true, propByDistrict.cheapest, propertyStreet);
                                    isStreetListed = true;
                                }
                            });

                            if (!isStreetListed) {
                                pricesAllStreets.push({
                                    street: propertyStreet.address.street,
                                    sumPrices: parseInt(propertyStreet.price),
                                    sumArea: parseInt(propertyStreet.area),
                                    count: 1,
                                    mostExpensive: propertyStreet,
                                    cheapest: propertyStreet
                                });
                                isStreetListed = true;
                            }
                        });

                        pricesAllDistricts.push({
                            district: district.district,
                            streets: pricesAllStreets
                        })
                    });

                    return pricesAllDistricts;
                }


                function calculateStreetStats() {

                    var pricesInDistricts = getStreetStats($scope.data);
                    var districtStatsByStreets = [];

                    pricesInDistricts.forEach(function (district) {

                        var streetsStat = []

                        district.streets.forEach(function (propByStreet) {
                            streetsStat.push(
                                {
                                    street: propByStreet.street,
                                    avgPriceMSquare: divideValues(propByStreet.sumPrices,propByStreet.sumArea),
                                    count: propByStreet.count,
                                    avgPrice: divideValues(propByStreet.sumPrices,propByStreet.count),
                                    mostExpensive: propByStreet.mostExpensive,
                                    cheapest: propByStreet.cheapest
                                }
                            );
                        });

                        districtStatsByStreets.push({
                            district: district.district,
                            streets: streetsStat
                        })
                    });

                    return districtStatsByStreets;
                }

                $scope.districtsStats = calculateDistrictsStats();
                $scope.streetStats = calculateStreetStats();


            }]
        };
    });
