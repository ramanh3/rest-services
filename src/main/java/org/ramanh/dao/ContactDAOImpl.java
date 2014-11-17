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
import org.springframework.stereotype.Repository;

@Repository
public class ContactDAOImpl {
	private SecureRandom random = new SecureRandom();
	private List<Contact> contacts;
	private Map<Integer, Contact> contactsMap;

	@PostConstruct
	protected void initUserMap() {
		contacts = new ArrayList<Contact>();
		contactsMap = new HashMap<>();
		random.setSeed(new Date().getTime());
		for (int i = 0; i < 15; i++) {
			Contact c = new Contact();
			int id = random.nextInt();
			c.setId(id);
			c.setName(String.format("test%s contact%s", i, i));
			c.setEmail(c.getName().replace(' ', '.') + "@email.com");
			c.setPassword(String.format("Secreat%s", i));
			Resident uk = new Resident();
			uk.setId(4);
			uk.setName("UK");
			List<Resident> residents = new ArrayList<Resident>();
			c.setResident(residents);
			contacts.add(c);
			contactsMap.put(id, c);
		}

		// logger.info("Preloaded %s users",usersMap.size());
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
