'use strict';

angular.module('propertyBrokerApp.controllers').
    controller('MainCtrl', function ($scope, uiGmapGoogleMapApi, PropertyService, $resource) {

        uiGmapGoogleMapApi.then(function() {
            $scope.map = { center: { latitude: 49.2044255, longitude: 16.6325489 }, zoom: 12 };
        });


        $scope.properties = [{Area: 33, Price: 1440000, Type: 'ONE_KK', Street: 'Kubíkova', District: 'Líšeň', City: 'Brno'},
                          {Area: 82, Price: 3190000, Type: 'THREE_ONE', Street: 'Bayerova', District: 'Veveří', City: 'Brno'},
                          {Area: 88, Price: 2900000, Type: 'FOUR_KK', Street: 'Vinohrady', District: 'Vinohrady', City: 'Brno'},
                          {Area: 59, Price: 2755113, Type: 'TWO_KK', Street: 'Kníničky', District: 'Kníničky', City: 'Brno'},
                          {Area: 171, Price: 7400000, Type: 'THREE_ONE', Street: 'Mojžíšova', District: 'Královo pole', City: 'Brno'},
                          {Area: 96, Price: 4100000, Type: 'THREE_KK', Street: 'Kotlanova', District: 'Líšeň', City: 'Brno'},
                          {Area: 67, Price: 2259000, Type: 'TWO_KK', Street: 'Bělohorská', District: 'Židenice', City: 'Brno'}];

        $scope.markers = [];
        var id = 0;

        function getCoords(url) {
            var resource = $resource(url, {});
            resource.get(function (data) {
                var latitude = data.results[0].geometry.location.lat;
                var longitude = data.results[0].geometry.location.lng;
                var marker = {id: id, coords: {latitude: latitude, longitude: longitude}};
                id++;
                $scope.markers.push(marker);
            });
    }

        for(var i = 0; i < $scope.properties.length; i++) {
            var url = 'https://maps.googleapis.com/maps/api/geocode/json?address=' + $scope.properties[i].City + '+' + $scope.properties[i].Street;
            getCoords(url);
        }

    });

