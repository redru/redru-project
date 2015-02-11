package com.redru.engine.exceptions;

public class ObjectAlreadyInStockException extends Exception {
	private static final long serialVersionUID = 1L;

	public ObjectAlreadyInStockException() {
		super("The selected identifier is already in the objects stock.");
	}
	
}
