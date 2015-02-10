package com.redru.engine.exceptions;

public class ActionAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public ActionAlreadyExistsException() {
		super("Cannot add a context that already exists.");
	}
	
}
