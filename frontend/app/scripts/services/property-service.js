'use strict';

angular.module('propertyBrokerApp.services')
    .service('PropertyService', ['PropertyResource', 'usSpinnerService', 'growl',
        function (PropertyResource, usSpinnerService, growl) {

            usSpinnerService.spin('spinner-1');

            function findAll(callback) {
                PropertyResource.findAll(
                    function(data) {
                        callback(data);
                    },
                    function(err) {
                        growl.error('Failed to connect to server. Please try again later.');
                        callback(err);
                    })
                    .$promise.finally(function() {
                        usSpinnerService.stop('spinner-1');
                    });
            }

            return {
                'findAll': findAll
            };

        }]
);

