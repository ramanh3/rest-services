﻿function ContactService($http) {
   
	this.getAll = function () {
       return $http.get('contacts');
    };
    
    this.get = function (conatctId) {
        return $http.get('contacts/'+conatctId);
    };
     
    this.update = function (conatct) {
         return $http.put('contacts',conatct);
    };

    this.add = function (conatct) {
        return $http.post('contacts',conatct);
   };
   
   this.delete = function (conatctId) {
       return $http.delete('contacts/'+conatctId);
  };
}