package com.redru.engine.actions;

public class ActionAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public ActionAlreadyExistsException() {
		super("Cannot exist two Actions with the same name.");
	}
	
}
