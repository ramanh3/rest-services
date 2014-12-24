function ContactService($http) {
  
    this.getAll = function () {
        return $http.get("contacts");
    };
    
    this.get = function (userId) {
        return $http.get("contacts/"+userId);
    };

    this.update = function (contact) {
        return $http.put("contacts",contact);
    };
    
    this.save = function (contact) {
        return $http.post("contacts",contact);
    };
    
    this.remove = function (userId) {
        return $http.delete("contacts/"+userId);
    };
}