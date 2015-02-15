package com.redru.engine.exceptions;

public class ModelAlreadyInStockException extends Exception {
	private static final long serialVersionUID = 1L;

	public ModelAlreadyInStockException() {
		super("The selected identifier is already in the models stock.");
	}
	
}
