'use strict';

angular.module('propertyBrokerApp', [
    'ngAnimate',
    'uiGmapgoogle-maps',
    'ui.bootstrap',
    'propertyBrokerApp.routes',
    'propertyBrokerApp.services',
    'propertyBrokerApp.controllers',
    'propertyBrokerApp.directives'
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
angular.module('propertyBrokerApp.directives', []);
