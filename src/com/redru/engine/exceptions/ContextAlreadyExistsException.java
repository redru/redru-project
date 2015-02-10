package com.redru.engine.exceptions;

public class ContextAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public ContextAlreadyExistsException() {
		super("The requested context was not found.");
	}
	
}
