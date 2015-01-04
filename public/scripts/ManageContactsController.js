﻿function ManageContactsController($scope, contactsService, $routeParams) {
	var responsePromise = contactsService.getAll() ; //async call
	responsePromise.success(
		function(data, status, headers, config) {
	 	//Executed on successful return from call
			 $scope.contacts = data;
			 if ($routeParams.userName !== undefined) {
			        $scope.userName = $routeParams.userName;
			    }
		}
	).error(
		function(data, status, headers, config) {
		//Executed on failure return from call
			console.log("Failed to get all contacts from server!")
		}
	);
   
};