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

   $scope.saveOrUpdate = function (conatct) {
	    var responsePromise = contactsService.update(conatct) ; //async call
		  responsePromise.success(
			function(data, status, headers, config) {
		 	//Executed on successful return from call
			 $location.path("/manage/"+conatct.name);
			}
		);
   }
   
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
    
    $scope.cancel = function(){
    	 $location.path("/manage/");
    }
};