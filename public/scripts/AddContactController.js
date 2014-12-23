/// <reference path="ContactsService.js" />
function AddContactController($scope, contactsService, $location,$routeParams) {
    $scope.contact = {
       "name": "shay",
        "phone": "33333",
        "email": "shay@rsa.com"
    };

    $scope.add = function () {
        contactsService.addContact($scope.contact);
        //alert($scope.contact.name);
        $location.path("/manage/"+$scope.contact.name);
    };

    $scope.setData = function () {
        $scope.contact.name = "Avi";
    };

    $scope.countries = [{
        id: '1',
        name: 'UK'
    },
    {
        id: '2',
        name: 'US'
    },
    {
        id: '3',
        name: 'Israel'
    }];
    
    if($routeParams.id){
    	var responsePromise = contactsService.get($routeParams.id);
   	    responsePromise.success(function(data, status, headers, config) {
   	        $scope.contact = data;
   	    });
    }
    
    $scope.save = function(contact){
    	var responsePromise = contactsService.save(contact);
   	    responsePromise.success(function(data, status, headers, config) {
   	    	$location.path("/manage/"+contact.name);
   	    });
    } 
    
};