package com.redru.engine.actions;

import java.util.ArrayList;

public class Context {
	private String identifier;
	private ArrayList<?> values;
	private boolean active;
	
	public Context(String identifier, ArrayList<?> values, boolean active) {
		this.identifier = identifier;
		this.values = values;
		this.active = active;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public ArrayList<?> getValues() {
		return values;
	}

	public void setValues(ArrayList<?> values) {
		this.values = values;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
