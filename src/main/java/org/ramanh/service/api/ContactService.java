package org.ramanh.service.api;

import java.util.List;

import org.ramanh.domain.Contact;

public interface ContactService {
	List<Contact> getContacts();
	Contact getContact(int id);
	void addContact(Contact contact);
	void updateContact(Contact contact);	
	void deleteContact(int id);
}
