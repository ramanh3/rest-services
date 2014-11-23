"use strict";
/**
 * test implementation for Contact service rest client using jquery 
 */
var App = App || {};
App.ContactsService = (function () {
    function ContactsService() {
        this.url = 'contacts';
    }

    /**
     * Call the rest service to read Contact
     * @param onSuccess - call back method to call on successful service return
     */
    ContactsService.prototype.getContact = function (onSuccess) {
    	$.ajax({
        	"type":"GET",
            "dataType": "json",
            "url": this.url,
            success: onSuccess
        });
    };
    
    /**
     * Call the rest service to save a Contact
     * @param onSuccess - call back method to call on successful service return
     */
    ContactsService.prototype.setContact = function (contact, onSuccess) {
        var data = JSON.stringify(contact);
        $.ajax({
        	"type":"POST",
            "dataType": "json",
            "url": this.url,
            "contentType":"application/json",
            "data": data,
            success: onSuccess
        });
    };   
    
    return ContactsService;
})();