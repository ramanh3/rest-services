package org.ramanh.service.rest;

import java.util.List;

import org.ramanh.dao.ContactDAOImpl;
import org.ramanh.domain.Contact;
import org.ramanh.service.api.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactServiceCtl implements ContactService {

	@Autowired
	private ContactDAOImpl contactDAO;

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public List<Contact> getContacts() {
		return contactDAO.getContacts();
	}

	@Override
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Contact getContact(@PathVariable int id) {
		return contactDAO.getContact(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED, reason = "New Object created")
	public void addContact(Contact contact) {
		contactDAO.saveOrUpdate(contact);
	}

	@Override
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Object Updates")
	public void updateContact(Contact contact) {
		contactDAO.saveOrUpdate(contact);
	}

	@Override
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Object Updates")
	public void deleteContact(int id) {
		contactDAO.delete(id);
	}

}
