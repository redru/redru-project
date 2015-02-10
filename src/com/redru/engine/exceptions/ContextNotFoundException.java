package com.redru.engine.exceptions;

public class ContextNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public ContextNotFoundException() {
		super("The requested context was not found.");
	}
	
}
