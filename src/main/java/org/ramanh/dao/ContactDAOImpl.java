package org.ramanh.dao;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Integer;

import javax.annotation.PostConstruct;

import org.ramanh.domain.Contact;
import org.ramanh.domain.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ContactDAOImpl {
	private static Logger LOG = LoggerFactory.getLogger(ContactDAOImpl.class);
	private SecureRandom random = new SecureRandom();
	private Map<Integer, Contact> contactsMap;

	@PostConstruct
	protected void initUserMap() {
		contactsMap = new HashMap<>();
		random.setSeed(new Date().getTime());
		LOG.info("==================================================================");
		LOG.info("|\tid\t|\tname\t|\temail\t|");
		for (int i = 0; i < 15; i++) {
			Contact c = new Contact();
			int id = random.nextInt();
			c.setId(id);
			c.setName(String.format("test%s", i, i));
			c.setEmail(c.getName().replace(' ', '.') + "@email.com");
			c.setPassword(String.format("Secret%s", i));
			c.setPhone(String.format("+555-8989%s%s", i, i));
			Resident uk = new Resident();

			uk.setId(4);
			uk.setName("UK");
			List<Resident> residents = new ArrayList<Resident>();
			c.setResident(residents);
			contactsMap.put(id, c);
			LOG.info("|{}|{}|{}|", c.getId(), c.getName(), c.getEmail());
		}
		LOG.info("==================================================================");
		LOG.info("Preloaded {} users", contactsMap.values().size());
	}

	public List<Contact> getContacts() {
		ArrayList<Contact> sortedContacts = new ArrayList<Contact>(contactsMap.values());
		Collections.sort(sortedContacts, new ContactNameComparator());
		return sortedContacts;
	}

	public Contact getContact(int id) {
		return contactsMap.get(id);
	}

	public void saveOrUpdate(Contact contact) {
		if (null == contact.getId()) {
			// This is a new contact
			// make sure we are not creating a duplicate contact name
			if (isContactNameExist(contact)) {
				throw new DuplicateContactException(contact.getName());
			}

			Integer id = getNextContactId();
			contact.setId(id);
		}
		contactsMap.put(contact.getId(), contact);
	}

	private Integer getNextContactId() {
		return random.nextInt();
	}

	public void delete(int id) {
		if (null != contactsMap.get(id)) {
			contactsMap.remove(id);
		}
	}

	protected boolean isContactNameExist(Contact newContact) {
		return contactsMap.containsValue(newContact);
	}

	private static class ContactNameComparator implements Comparator<Contact> {

		@Override
		public int compare(Contact c1, Contact c2) {
			String name1 = c1.getName();
			String name2 = c2.getName();
			return name1.compareTo(name2);
		}

	}
}
