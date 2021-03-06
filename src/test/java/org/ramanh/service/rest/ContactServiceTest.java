package org.ramanh.service.rest;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ramanh.Application;
import org.ramanh.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class ContactServiceTest {

	private static String CONTENT_TYPE = String.format("%s;%S", MediaType.APPLICATION_JSON, "charset=UTF-8");
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	protected ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testListContacts() throws Exception {
		MockHttpServletRequestBuilder request = get("/contacts").accept(MediaType.APPLICATION_JSON);
		ResultActions perform = mockMvc.perform(request);
		perform.andExpect(status().isOk());
		perform.andExpect(content().contentType(CONTENT_TYPE));
		perform.andExpect(jsonPath("$").isArray());
		MockHttpServletResponse contactsResponse = perform.andReturn().getResponse();
		String contactsList = contactsResponse.getContentAsString();
		Contact[] contacts = mapper.readValue(contactsList, Contact[].class);
		Assert.assertThat(contacts.length, greaterThanOrEqualTo(15));
	}

	@Test
	public void testGetContact() throws Exception {
		Contact contact = getFirstContact();
		MockHttpServletRequestBuilder request = get("/contacts/" + contact.getId());
		ResultActions perform = mockMvc.perform(request);
		perform.andExpect(status().isOk());
		perform.andExpect(content().contentType(CONTENT_TYPE));
		perform.andExpect(jsonPath("$.id").value(contact.getId()));
		perform.andExpect(jsonPath("$.email").value(contact.getEmail()));
		perform.andExpect(jsonPath("$.name").value(contact.getName()));
		perform.andExpect(jsonPath("$.password").value(contact.getPassword()));
	}

	@Test
	public void testUpdateContact() throws Exception {
		Contact contact = getFirstContact();
		contact.setName(contact.getName() + " updated");
		String updatedsContactJson = mapper.writeValueAsString(contact);
		MockHttpServletRequestBuilder request = put("/contacts").content(updatedsContactJson).contentType(MediaType.APPLICATION_JSON);
		;
		ResultActions perform = mockMvc.perform(request);
		perform.andExpect(status().isNoContent());
		Contact contactUpdated = getContactById(contact.getId());
		assertThat("Contact not matching updated one",contact,  equalTo(contactUpdated));
		assertThat("Contact id was changed after update",contact.getId(),  equalTo(contactUpdated.getId()));
	}

	@Test
	public void testAddContact() throws Exception {
		Contact contact = getFirstContact();
		contact.setId(null);
		contact.setName(contact.getName() + " clone");
		String newContactJson = mapper.writeValueAsString(contact);
		MockHttpServletRequestBuilder request = post("/contacts").content(newContactJson).contentType(MediaType.APPLICATION_JSON);
		ResultActions perform = mockMvc.perform(request);
		perform.andExpect(status().isCreated());
		//Contact newContact = getContactById(contact.getId());
		//assertThat("Contact not matching updated one",contact,  equalTo(newContact));
		//assertNotNull("New contact should have and id",newContact.getId());
	}

	private Contact getFirstContact() throws Exception, UnsupportedEncodingException, IOException, JsonParseException, JsonMappingException {
		MockHttpServletRequestBuilder request = get("/contacts").accept(MediaType.APPLICATION_JSON);
		ResultActions perform = mockMvc.perform(request);
		perform.andExpect(status().isOk());
		perform.andExpect(jsonPath("$").isArray());
		MockHttpServletResponse contactsResponse = perform.andReturn().getResponse();
		String contactsList = contactsResponse.getContentAsString();
		Contact[] contacts = mapper.readValue(contactsList, Contact[].class);
		return contacts[0];
	}

	private Contact getContactById(Integer id) throws Exception {
		MockHttpServletRequestBuilder request = get(String.format("/contacts/%s", id)).accept(MediaType.APPLICATION_JSON);
		ResultActions perform = mockMvc.perform(request);
		MockHttpServletResponse contactsResponse = perform.andReturn().getResponse();
		perform.andExpect(status().isOk());
		String contactStr = contactsResponse.getContentAsString();
		Contact contact = mapper.readValue(contactStr, Contact.class);
		return contact;
	}
	
	@Test
	public void testAddSameContact() throws Exception {
		Contact contact = getFirstContact();
		contact.setId(null);
		contact.setName(contact.getName() + " clone same name");
		String newContactJson = mapper.writeValueAsString(contact);
		MockHttpServletRequestBuilder request = post("/contacts").content(newContactJson).contentType(MediaType.APPLICATION_JSON);
		ResultActions perform = mockMvc.perform(request);
		perform.andExpect(status().isCreated());
		
		contact.setId(null);
		String newContactJson2 = mapper.writeValueAsString(contact);
		MockHttpServletRequestBuilder request2 = post("/contacts").content(newContactJson2).contentType(MediaType.APPLICATION_JSON);
		ResultActions perform2 = mockMvc.perform(request2);
		perform2.andExpect(status().is(HttpStatus.CONFLICT.value()));
	}
}
