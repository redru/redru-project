package com.redru.engine.time;


public abstract class TimeObject {
	
	private String identifier;
	private int timeLimit;
	private int timeCount;
	private boolean active;
// CONSTRUCTOR -----------------------------------------------------------------------
	public TimeObject() {
		this("", 0, 0, false);
	}
	
	public TimeObject(String identifier, int timeLimit, int timeCount, boolean active) {
		this.identifier = identifier;
		this.timeLimit = timeLimit;
		this.timeCount = timeCount;
		this.active = active;
	}
	
	protected final void update() {
		if (++timeCount >= timeLimit) {
			timeCount = 0;
			this.timeAction();
		}
	}
	
	protected abstract void timeAction();
// -----------------------------------------------------------------------------------
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getTimeCount() {
		return timeCount;
	}

	public void setTimeCount(int timeCount) {
		this.timeCount = timeCount;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
// -----------------------------------------------------------------------------------	
}