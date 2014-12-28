/// <reference path="AddContactController.js" />
(function (){
    var MODULE_NAME = "phonebook";
    var phoneBookApp = angular.module(MODULE_NAME, ['multi-select', 'ui.bootstrap', 'ngRoute']);

    phoneBookApp.controller('AddContactController', AddContactController);
    phoneBookApp.controller('ManageContactsController', ManageContactsController);

    phoneBookApp.filter('prettyArrayPrint', function () {
        return function (inputArray, propertyName) {
            var ans = "";
            if (inputArray && inputArray.length > 0) {
                ans = inputArray[0][propertyName];
                for (var i = 1; i < inputArray.length ; i++) {
                    ans += "," + inputArray[i][propertyName];
                };
            };
            return ans;

        };
    });

    phoneBookApp.config(function ($routeProvider, $httpProvider) {
    	  $httpProvider.interceptors.push(function($q,$rootScope) {
    	    return {
    	     	'responseError': function(response) {
    	        if(response.status == '409') {
    	        	var errorInfo = response.data;
    	            console.info('Error code' + errorInfo.errorCode + ": " + errorInfo.message);
    	            $rootScope.error = errorInfo;
    	        }
    	        return $q.reject(response);
    	      }
    	    };
    	  });
    });
    
    phoneBookApp.config(function ($routeProvider) {
        $routeProvider
		// route for the about page
			.when('/', {
			    templateUrl: 'templates/add-contact.html',
			    controller: 'AddContactController'
			}).when('/manage', {
			    templateUrl: 'templates/manage-contacts.html',
			    controller: 'ManageContactsController'
			
			}).when('/manage/:userName', {
			    templateUrl: 'templates/manage-contacts.html',
			    controller: 'ManageContactsController'

			}).when('/edit/:id', {
			    templateUrl: 'templates/add-contact.html',
			    controller: 'AddContactController'
			});

    });

    phoneBookApp.service("contactsService", ContactService);
    phoneBookApp.directive('rsaContact', function () {
        var directive = {};
        directive.scope = {
            contact: "=contact"
        };
        directive.controller =  function ($scope) {
            $scope.isOpen = true;
        }

        directive.restrict = 'EA';
        directive.templateUrl = "templates/contact.html";
        return directive;

    });

})();