﻿function ManageContactsController($scope, contactsService, $routeParams) {
    var responsePromise = contactsService.getAll();

    responsePromise.success(function(data, status, headers, config) {
        $scope.contacts = data;
    });
};