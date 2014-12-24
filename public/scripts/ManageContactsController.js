﻿function ManageContactsController($scope, contactsService, $routeParams) {
    var responsePromise = contactsService.getAll();
    responsePromise.success(function(data, status, headers, config) {
    	if ($routeParams.userName !== undefined) {
    	        $scope.userName = $routeParams.userName;
    	}
        $scope.contacts = data;
    });
};