package org.ramanh.dao;

public class DuplicateContactException extends RuntimeException {

	private static final long serialVersionUID = -4764245345752498665L;

	public DuplicateContactException(String message) {
		super(message);
	}
	
}
