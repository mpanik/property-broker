'use strict';

angular.module('propertyBrokerApp', [
    'ngAnimate',
    'uiGmapgoogle-maps',
    'propertyBrokerApp.routes',
    'propertyBrokerApp.services',
    'propertyBrokerApp.controllers'
])
    .config(function (uiGmapGoogleMapApiProvider) {
        uiGmapGoogleMapApiProvider.configure({
            v: '3.17',
            libraries: 'weather,geometry,visualization'
        });
    });

angular.module('propertyBrokerApp.routes', ['ngRoute']);
angular.module('propertyBrokerApp.services', ['ngResource']);
angular.module('propertyBrokerApp.controllers', []);
