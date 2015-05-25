'use strict';

angular.module('propertyBrokerApp.routes')
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'views/main.html',
                controller: 'MainCtrl'
            })
            .state('stats', {
                url: '/stats',
                templateUrl: 'views/stats.html',
                controller: 'StatsCtrl'
            });

    }]
);
