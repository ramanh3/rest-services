package org.ramanh.service.rest;

import java.util.List;

import javax.inject.Inject;

import org.ramanh.dao.ContactDAOImpl;
import org.ramanh.domain.Contact;
import org.ramanh.service.api.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ContactServiceImpl implements ContactService {
	@Inject
	private ContactDAOImpl contactDAO;

	@RequestMapping(value = "contacts", method = RequestMethod.GET)
	public List<Contact> getContacts(){
		return contactDAO.getContacts();
	}

	@RequestMapping(value = "/contacts/{id}", method = RequestMethod.GET)
	public Contact getContact(@PathVariable int id) {
		return contactDAO.getContact(id);
	}
	
	@RequestMapping(value = "/contacts", method = RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED,reason="New Object created")	
	public void addContact(@RequestBody Contact contact){
		contactDAO.saveOrUpdate(contact);
	}

	@RequestMapping(value = "/contacts", method = RequestMethod.PUT)
	@ResponseStatus(value=HttpStatus.NO_CONTENT,reason="Object Updates")	
	public void updateContact(@RequestBody  Contact contact,BindingResult bindingResult){
		contactDAO.saveOrUpdate(contact);		
	}
	
	@RequestMapping(value = "/contacts/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.NO_CONTENT,reason="Object Deleted")	
	public void deleteContact(@PathVariable int id) {
		contactDAO.delete(id);
	}
	
	
}