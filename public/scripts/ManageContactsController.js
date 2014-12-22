﻿function ManageContactsController($scope, contactsService, $routeParams) {
    $scope.contacts = contactsService.getAll();
    if ($routeParams.userName !== undefined) {
        $scope.userName = $routeParams.userName;
    }
};