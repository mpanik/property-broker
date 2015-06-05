'use strict';

angular.module('propertyBrokerApp.services')
    .factory('PropertyResource', ['$resource', function ($resource) {

        var baseUrl = 'http://localhost:8080/pbroker/rest/properties';

        return $resource(baseUrl, {}, {
            findAll: {method: 'GET', isArray:true}
        });

    }]
);

