﻿/// <reference path="ContactsService.js" />
function AddContactController($scope, contactsService, $routeParams, $location) {
   if($routeParams.id){
	  var conatctId = $routeParams.id;
	  var responsePromise = contactsService.get(conatctId) ; //async call
	  responsePromise.success(
		function(data, status, headers, config) {
	 	//Executed on successful return from call
			 $scope.contact = data;			 
		}
	);
   }

   $scope.saveOrUpdate = function (contact) {
	   
	   var responsePromise ;
	    if(contact.id){
	    	responsePromise = contactsService.update(contact) ; //async call
	    }else{
	    	responsePromise = contactsService.add(contact) ; //async call	
	    }
	    
		responsePromise.success(
			function(data, status, headers, config) {
		 	//Executed on successful return from call
			 $location.path("/manage/"+contact.name);
			}
		);
   }
   
   $scope.delete = function (contactId) {
	   var responsePromise = contactsService.delete(conatctId) ; //async call
		  responsePromise.success(
			function(data, status, headers, config) {
			//Executed on successful return from call
			 $location.path("/manage/deleted");
			}
		);
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
    
    $scope.cancel = function(){
    	 $location.path("/manage/");
    }
};