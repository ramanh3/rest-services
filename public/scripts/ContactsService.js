function ContactService($http) {
    this.contactsList = [
         {
             'name': 'Shay',
             'password': '123456',
             'email': 'shay@rsa.com',
             'resident': [{
                 'id': '4',
                 'id': 'UK'
             }]
         },
         {
             'name': 'Eran',
             'password': '123456',
             'email': 'eran@rsa.com',
             'resident': [{
                 'id': '4',
                 'id': 'UK'
             }]
         }

    ];

    this.addContact = function (contact) {
        this.contactsList.push(contact);
    };

    this.getAll = function () {
        return $http.get("contacts");
    };
    
    this.get = function (userId) {
        return $http.get("contacts/"+userId);
    };

    this.save = function (contact) {
        return $http.put("contacts",contact);
    };
}