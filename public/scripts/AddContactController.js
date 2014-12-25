/// <reference path="ContactsService.js" />
function AddContactController($scope, contactsService, $location,$routeParams) {
	 if($routeParams.id){
	    	var responsePromise = contactsService.get($routeParams.id);
	   	    responsePromise.success(function(data, status, headers, config) {
	   	        $scope.contact = data;
	   	    });
	 }
	
	$scope.cancel = function(){
		 $routeParams.id = null;
		 $location.path('/manage')	 
	}
	  
	$scope.add = function () {
        contactsService.addContact($scope.contact);
        //alert($scope.contact.name);
        $location.path("/manage/"+$scope.contact.name);
    };

    $scope.saveOrUpdate = function(contact){
    	var responsePromise;
    	
    	if(contact.id){
    		responsePromise = contactsService.update(contact);
    	}else{
    		responsePromise = contactsService.save(contact);	
    	}
    	
   	    responsePromise.success(function(data, status, headers, config) {
   	    	$location.path("/manage/"+contact.name);
   	    }).error(function(data, status, headers, config){
   	    	$scope.error = data.message;
   	    })
    } 
    
    $scope.remove = function(id){
    	var responsePromise = contactsService.remove(id);
    	responsePromise.success(function(data, status, headers, config) {
   	    	$location.path("/manage/deleted");
   	    });
    } 
    
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
};