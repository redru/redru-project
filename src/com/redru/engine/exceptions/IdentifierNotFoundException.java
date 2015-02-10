package com.redru.engine.exceptions;

public class IdentifierNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public IdentifierNotFoundException() {
		super("The requested identifier was not found.");
	}
	
}
