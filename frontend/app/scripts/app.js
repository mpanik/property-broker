'use strict';

angular.module('propertyBrokerApp', [
    'ngAnimate',
    'ui.bootstrap',
    'angular-growl',
    'ngLodash',
    'angularSpinner',
    'propertyBrokerApp.routes',
    'propertyBrokerApp.services',
    'propertyBrokerApp.controllers',
    'propertyBrokerApp.directives'
])
    .config(['uiGmapGoogleMapApiProvider', 'growlProvider',
        function (uiGmapGoogleMapApiProvider, growlProvider) {
            uiGmapGoogleMapApiProvider.configure({
                v: '3.17',
                libraries: 'weather,geometry,visualization'
            });

            growlProvider.globalTimeToLive(5000);
        }]);

angular.module('propertyBrokerApp.routes', ['ui.router']);
angular.module('propertyBrokerApp.services', ['ngResource']);
angular.module('propertyBrokerApp.controllers', ['uiGmapgoogle-maps']);
angular.module('propertyBrokerApp.directives', []);
