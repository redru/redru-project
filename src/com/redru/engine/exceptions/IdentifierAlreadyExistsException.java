package com.redru.engine.exceptions;

public class IdentifierAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public IdentifierAlreadyExistsException() {
		super("Action identifier already exists. Cannot add an action with the same identifier.");
	}
	
}
