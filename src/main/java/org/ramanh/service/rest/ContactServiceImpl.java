package org.ramanh.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.ramanh.domain.Contact;
import org.ramanh.domain.Resident;
import org.ramanh.service.api.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ContactServiceImpl implements ContactService {
	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public List<Contact> getContacts(){
		List<Contact> contacts = new ArrayList<Contact>();
		Contact c = new Contact();
		c.setName("test contact");
		c.setEmail("test.contact@email.com");
		c.setPassword("Secreat 123");
		Resident uk = new Resident();
		uk.setId(4);
		uk.setName("UK");
		List<Resident> residents = new ArrayList<Resident>();
		c.setResident(residents);
		contacts.add(c);
		return contacts;
	}
		
}