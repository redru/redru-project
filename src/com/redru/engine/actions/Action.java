package com.redru.engine.actions;

public abstract class Action {
	private String identifier;
	private boolean executeOnce;
	
	public Action(String identifier, boolean executeOnce) {
		this.identifier = identifier;
		this.executeOnce = executeOnce;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public boolean isExecuteOnce() {
		return executeOnce;
	}

	public void setExecuteOnce(boolean executeOnce) {
		this.executeOnce = executeOnce;
	}

	public abstract void execute(ActionContext<?> context);
	
}
