'use strict';

angular.module('propertyBrokerApp.controllers').
    controller('MainCtrl', function ($scope, uiGmapGoogleMapApi, PropertyService, $resource) {

        uiGmapGoogleMapApi.then(function() {
            $scope.map = { center: { latitude: 49.2044255, longitude: 16.6325489 }, zoom: 12 };
        });

        $scope.markers = [];

        /*$scope.properties = [{Area: 33, Price: 1440000, Type: 'ONE_KK', Street: 'Kubíkova', District: 'Líšeň', City: 'Brno', x: 49.20282, y: 16.564736},
                          {Area: 82, Price: 3190000, Type: 'THREE_ONE', Street: 'Bayerova', District: 'Veveří', City: 'Brno', x: 49.2299654, y: 16.593883},
                          {Area: 88, Price: 2900000, Type: 'FOUR_KK', Street: 'Vinohrady', District: 'Vinohrady', City: 'Brno', x: 49.225979, y: 16.555563},
                          {Area: 59, Price: 2755113, Type: 'TWO_KK', Street: 'Kníničky', District: 'Kníničky', City: 'Brno', x: 49.2007647, y: 16.6058647},
                          {Area: 171, Price: 7400000, Type: 'THREE_ONE', Street: 'Mojžíšova', District: 'Královo pole', City: 'Brno', x: 49.1808461, y: 16.598696},
                          {Area: 96, Price: 4100000, Type: 'THREE_KK', Street: 'Kotlanova', District: 'Líšeň', City: 'Brno', x: 49.228787, y: 16.5260415},
                          {Area: 67, Price: 2259000, Type: 'TWO_KK', Street: 'Bělohorská', District: 'Židenice', City: 'Brno', x: 49.191932, y: 16.618645}];*/




    });

