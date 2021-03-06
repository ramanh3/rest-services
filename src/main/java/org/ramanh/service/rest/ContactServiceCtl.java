package org.ramanh.service.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ramanh.dao.ContactDAOImpl;
import org.ramanh.dao.DuplicateContactException;
import org.ramanh.domain.Contact;
import org.ramanh.service.api.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/contacts")
public class ContactServiceCtl implements ContactService{

	private static Logger LOG = LoggerFactory.getLogger(ContactServiceCtl.class);
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
	
	@Override
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED, reason = "New Object created")
	public void addContact(@RequestBody Contact contact) {
		contactDAO.saveOrUpdate(contact);
		LOG.info("Contact {} created",contact);
	}

	@Override
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Object Updates")
	public void updateContact(@RequestBody Contact contact) {
		contactDAO.saveOrUpdate(contact);
		LOG.info("Contact {} updated",contact);
	}

	@Override
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Object Updates")
	public void deleteContact(@PathVariable int id) {
		contactDAO.delete(id);
		LOG.info("Contact with id {} deleted",id);
	}

	@ExceptionHandler(DuplicateContactException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, String> handleDuplicateContactException(HttpServletRequest req, DuplicateContactException ex) {

		Map<String, String> errorInfo = new HashMap<>();
		errorInfo.put("errorCode", "900409");
		errorInfo.put("message", String.format("Failed to create Contact, a contact with name %s aleady exists!", ex.getMessage()));
		return errorInfo;

	}
}
