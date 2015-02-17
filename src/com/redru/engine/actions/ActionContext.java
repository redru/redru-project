package com.redru.engine.actions;

import java.util.ArrayList;

public class ActionContext<T extends Object> {
	private String identifier;
	private ArrayList<T> values;
	private boolean active;
	
	public ActionContext(String identifier, ArrayList<T> values, boolean active) {
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

	public ArrayList<T> getValues() {
		return values;
	}

	public void setValues(ArrayList<T> values) {
		this.values = values;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
