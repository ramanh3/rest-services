package org.ramanh.dao;

import java.security.SecureRandom;
import java.util.ArrayList;
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
	private List<Contact> contacts;
	private Map<Integer, Contact> contactsMap;

	@PostConstruct
	protected void initUserMap() {
		contacts = new ArrayList<Contact>();
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
			c.setPhone(String.format("+555-8989%s%s", i,i));
			Resident uk = new Resident();
			
			uk.setId(4);
			uk.setName("UK");
			List<Resident> residents = new ArrayList<Resident>();
			c.setResident(residents);
			contacts.add(c);
			contactsMap.put(id, c);
			LOG.info("|{}|{}|{}|",c.getId(),c.getName(),c.getEmail());
		}
		LOG.info("==================================================================");
		LOG.info("Preloaded {} users",contacts.size());
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public Contact getContact(int id) {
		return contactsMap.get(id);
	}

	public void saveOrUpdate(Contact contact) {
		if (null == contact.getId()) {
			Integer id = getNextContactId();
			contact.setId(id);
		}
		contacts.add(contact);
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

}
