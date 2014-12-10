function ManageContactsController($scope, contactsService, $routeParams) {
    $scope.contacts = contactsService.query();
    if ($routeParams.userName !== undefined) {
        $scope.userName = $routeParams.userName;
    }
};