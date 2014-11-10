package org.ramanh.domain;

import java.util.List;

public class Contact {
	private String name;
	private String password;
	private String email;
	private List<Resident> resident;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Resident> getResident() {
		return resident;
	}

	public void setResident(List<Resident> resident) {
		this.resident = resident;
	}
}
