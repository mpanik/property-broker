'use strict';

angular.module('propertyBrokerApp.directives')
    .directive('statistics', function () {
        return {
            templateUrl: 'scripts/directives/statistics-directive/avgPrice-directive.html',
            restrict: 'E',
            scope: {
                data: '='
            },
            controller: ['$scope', function ($scope) {

                $scope.$watch('values', function (newValue) {
                    $scope.data = newValue;
                }, true);

                //TODO: load data from database
                var properties =
                    [{Area: 33, Price: 1440000, Type: 'ONE_KK', Street: 'Kubíkova', District: 'Líšeň', City: 'Brno'},
                     {Area: 67, Price: 2000000, Type: 'THREE_KK', Street: 'Kubíkova', District: 'Líšeň', City: 'Brno'},
                     {Area: 82, Price: 3190000, Type: 'THREE_ONE', Street: 'Bayerova', District: 'Veveří', City: 'Brno'},
                     {Area: 88, Price: 2900000, Type: 'FOUR_KK', Street: 'Vinohrady', District: 'Vinohrady', City: 'Brno'},
                     {Area: 59, Price: 2755113, Type: 'TWO_KK', Street: 'Kníničky', District: 'Kníničky', City: 'Brno'},
                     {Area: 171, Price: 7400000, Type: 'THREE_ONE', Street: 'Mojžíšova', District: 'Královo pole', City: 'Brno'},
                     {Area: 96, Price: 4100000, Type: 'THREE_KK', Street: 'Kotlanova', District: 'Líšeň', City: 'Brno'},
                     {Area: 67, Price: 2259000, Type: 'TWO_KK', Street: 'Bělohorská', District: 'Židenice', City: 'Brno'}];

                function getMinMaxPrice(boolMin, propertyA, propertyB) {
                    var price;
                    if (boolMin === true) {
                        price = Math.min(parseInt(propertyA.Price), parseInt(propertyB.Price));
                    } else {
                        price = Math.max(parseInt(propertyA.Price), parseInt(propertyB.Price));
                    }

                    if (parseInt(propertyB.Price) === parseInt(price)) {
                        return propertyB;
                    } else {
                        return propertyA;
                    }
                };


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
                            if (property.District === propByDistrict.District && !isDistrictListed) {
                                propByDistrict.SumPrices =sumValues(propByDistrict.SumPrices,property.Price);
                                propByDistrict.SumArea = sumValues(propByDistrict.SumArea,property.Area);
                                propByDistrict.Count = sumValues(propByDistrict.Count,1);
                                propByDistrict.MostExpensive = getMinMaxPrice(false, propByDistrict.MostExpensive, property);
                                propByDistrict.Cheapest = getMinMaxPrice(true, propByDistrict.Cheapest, property);
                                propByDistrict.Properties.push(property);
                                isDistrictListed = true;
                            }
                        });
                        if (!isDistrictListed) {
                            pricesInDistricts.push({
                                District: property.District,
                                SumPrices: parseInt(property.Price),
                                SumArea: parseInt(property.Area),
                                Count: 1,
                                MostExpensive: property,
                                Cheapest: property,
                                Properties: [property]
                            });
                        }
                    });
                    return pricesInDistricts;
                }

                function calculateDistrictsStats() {

                    var pricesInDistricts = getDistrictStats(properties);
                    var avgPricesInDistricts = [];

                    pricesInDistricts.forEach(function (propByDistrict) {
                        avgPricesInDistricts.push(
                            {
                                District: propByDistrict.District,
                                AvgPriceMSquare: divideValues(propByDistrict.SumPrices, propByDistrict.SumArea),
                                Count: propByDistrict.Count,
                                AvgPrice: divideValues(propByDistrict.SumPrices,propByDistrict.Count),
                                MostExpensive: propByDistrict.MostExpensive,
                                Cheapest: propByDistrict.Cheapest
                            }
                        );
                    });
                    return avgPricesInDistricts;
                }

                function getStreetStats() {

                    var valueDistrictStreet = getDistrictStats(properties);
                    var pricesAllDistricts = [];

                    valueDistrictStreet.forEach(function (district) {

                        var pricesAllStreets = [];

                        district.Properties.forEach(function (propertyStreet) {

                            var isStreetListed = false;

                            pricesAllStreets.forEach(function (propByDistrict) {
                                if (propertyStreet.Street === propByDistrict.Street && !isStreetListed) {
                                    propByDistrict.SumPrices = sumValues(propByDistrict.SumPrices,propertyStreet.Price);
                                    propByDistrict.SumArea = sumValues(propByDistrict.SumArea,propertyStreet.Area);
                                    propByDistrict.Count =sumValues(propByDistrict.Count,1);
                                    propByDistrict.MostExpensive = getMinMaxPrice(false, propByDistrict.MostExpensive, propertyStreet);
                                    propByDistrict.Cheapest = getMinMaxPrice(true, propByDistrict.Cheapest, propertyStreet);
                                    isStreetListed = true;
                                }
                            });

                            if (!isStreetListed) {
                                pricesAllStreets.push({
                                    Street: propertyStreet.Street,
                                    SumPrices: parseInt(propertyStreet.Price),
                                    SumArea: parseInt(propertyStreet.Area),
                                    Count: 1,
                                    MostExpensive: propertyStreet,
                                    Cheapest: propertyStreet
                                });
                                isStreetListed = true;
                            }
                        })

                        pricesAllDistricts.push({
                            District: district.District,
                            Streets: pricesAllStreets
                        })
                    })

                    return pricesAllDistricts;
                }


                function calculateStreetStats() {

                    var pricesInDistricts = getStreetStats(properties);
                    var districtStatsByStreets = [];

                    pricesInDistricts.forEach(function (district) {

                        var streetsStat = []

                        district.Streets.forEach(function (propByStreet) {
                            streetsStat.push(
                                {
                                    Street: propByStreet.Street,
                                    AvgPriceMSquare: divideValues(propByStreet.SumPrices,propByStreet.SumArea),
                                    Count: propByStreet.Count,
                                    AvgPrice: divideValues(propByStreet.SumPrices,propByStreet.Count),
                                    MostExpensive: propByStreet.MostExpensive,
                                    Cheapest: propByStreet.Cheapest
                                }
                            );
                        });

                        districtStatsByStreets.push({
                            District: district.District,
                            Streets: streetsStat
                        })
                    })

                    return districtStatsByStreets;
                }

                $scope.districtsStats = calculateDistrictsStats();
                $scope.streetStats = calculateStreetStats();

            }]
        };
    });
