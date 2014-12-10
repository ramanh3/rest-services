/// <reference path="AddContactController.js" />
(function (){
    var MODULE_NAME = "phonebook";
    var phoneBookApp = angular.module(MODULE_NAME, ['multi-select', 'ui.bootstrap', 'ngRoute' ,'ngResource']);

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

			});

    });


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

    phoneBookApp.service("contactsService", ContactService);

})();