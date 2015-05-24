'use strict';

angular.module('propertyBrokerApp.controllers').
    controller('MainCtrl', function ($scope, uiGmapGoogleMapApi, PropertyService) {

        $scope.marker = {};
        $scope.marker.id = 0;
        $scope.markers = [];

        $scope.$watch('marker', function (newValue) {
            $scope.marker = newValue;
        }, true);

        uiGmapGoogleMapApi.then(function() {
            $scope.map = { center: { latitude: 49.2044255, longitude: 16.6325489 }, zoom: 12 };
        });

       /*$scope.properties = [{"id":1,"area":54,"price":1389000,"address":{"street":"Křepelčí","district":"Bystrc","city":"Brno"},"type":"TWO_KK","coords":{"x":49.2165092,"y":16.497189}},
                            {"id":2,"area":43,"price":1943000,"address":{"street":"Táborská","district":"Židenice","city":"Brno"},"type":"ONE_KK","coords":{"x":49.1943165,"y":16.6416975}},
                            {"id":3,"area":50,"price":1599000,"address":{"street":"Oderská","district":"Starý lískovec","city":"Brno"},"type":"TWO_KK","coords":{"x":49.1704338,"y":16.5612581}},
                            {"id":4,"area":61,"price":2790000,"address":{"street":"Štýřice","district":"Štýřice","city":"Brno"},"type":"TWO_KK","coords":{"x":49.1808798,"y":16.5953162}},
                            {"id":5,"area":43,"price":1826000,"address":{"street":"Rybářská","district":"Střed","city":"Brno"},"type":"ONE_KK","coords":{"x":49.1871319,"y":16.588655}},
                            {"id":6,"area":47,"price":1990000,"address":{"street":"Výstavní","district":"Staré brno","city":"Brno"},"type":"TWO_ONE","coords":{"x":49.1891444,"y":16.5890844}},
                            {"id":7,"area":27,"price":1350000,"address":{"street":"Žitná","district":"Řečkovice","city":"Brno"},"type":"ONE_KK","coords":{"x":49.23984799999999,"y":16.588552}},
                            {"id":8,"area":55,"price":1550000,"address":{"street":"Teyschlova","district":"Bystrc","city":"Brno"},"type":"TWO_ONE","coords":{"x":49.217952,"y":16.5018147}},
                            {"id":9,"area":69,"price":2049000,"address":{"street":"Mutěnická","district":"Židenice","city":"Brno"},"type":"THREE_ONE","coords":{"x":49.20299,"y":16.654796}},
                            {"id":10,"area":98,"price":3600000,"address":{"street":"Nerudova","district":"Veveří","city":"Brno"},"type":"TWO_ONE","coords":{"x":49.205983,"y":16.595174}},
                            {"id":11,"area":38,"price":2200000,"address":{"street":"Příkop","district":"Zábrdovice","city":"Brno"},"type":"TWO_KK","coords":{"x":49.1998893,"y":16.6145083}},
                            {"id":12,"area":122,"price":3750000,"address":{"street":"Veveří","district":"Veveří","city":"Brno"},"type":"FOUR_ONE","coords":{"x":49.202337,"y":16.595228}},
                            {"id":13,"area":65,"price":2250000,"address":{"street":"Uzbecká","district":"Bohunice","city":"Brno"},"type":"THREE_ONE","coords":{"x":49.172343,"y":16.583318}},
                            {"id":14,"area":86,"price":5354262,"address":{"street":"Zoubkova","district":"Nový lískovec","city":"Brno"},"type":"THREE_KK","coords":{"x":49.1836733,"y":16.5621693}}];
        */
        PropertyService.findAll(
            function(data) {
                $scope.properties = data;
                $scope.serverError = false;
            },
            function() {
                $scope.serverError = true;
            }
        );

    });





