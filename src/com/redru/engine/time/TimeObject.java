package com.redru.engine.time;


public abstract class TimeObject {
	public enum TimeType {
		FRAME_BASED,
		ELAPSED_TIME_BASED
	}
	
	private String identifier;
	private int timeLimit;
	private int timeCount;
	private long timeElapsedLimit;
	private long timeElapsedCount;
	private boolean active;
	private TimeType timeType;
// CONSTRUCTOR -----------------------------------------------------------------------
	public TimeObject() {
		this("", 0, 0, false);
	}
	
	public TimeObject(String identifier, int timeLimit, int timeCount, boolean active) {
		this.identifier = identifier;
		this.timeLimit = timeLimit;
		this.timeCount = timeCount;
		this.active = active;
		this.timeType = TimeType.FRAME_BASED;
	}
	
	public TimeObject(String identifier, long timeElapsedLimit, long timeElapsedCount, boolean active) {
		this.identifier = identifier;
		this.timeElapsedLimit = timeElapsedLimit;
		this.timeElapsedCount = timeElapsedCount;
		this.active = active;
		this.timeType = TimeType.ELAPSED_TIME_BASED;
	}
	
	protected final void updateFrameTime() {
		if (++timeCount >= timeLimit) {
			this.timeCount = 0;
			this.timeAction();
		}
	}
	
	protected final void updateElapsedTime(long timeElapsed) {
		this.timeElapsedCount += timeElapsed;
		if (this.timeElapsedCount >= timeElapsedLimit) {
			this.timeElapsedCount = 0;
			this.timeAction();
		}
	}
	
	public void configure(int timeLimit, int timeCount, boolean active) {
		this.timeLimit = timeLimit;
		this.timeCount = timeCount;
		this.active = active;
		this.timeType = TimeType.FRAME_BASED;
	}
	
	public void configure(long timeElapsedLimit, long timeElapsedCount, boolean active) {
		this.timeElapsedLimit = timeElapsedLimit;
		this.timeElapsedCount = timeElapsedCount;
		this.active = active;
		this.timeType = TimeType.ELAPSED_TIME_BASED;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( !(obj instanceof TimeType) ) {
			return false;
		} else {
			if (this.timeType.equals((TimeType) obj)) {
				return true;
			} else {
				return false;
			}
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
	
	public long getTimeElapsedLimit() {
		return timeElapsedLimit;
	}

	public void setTimeElapsedLimit(long timeElapsedLimit) {
		this.timeElapsedLimit = timeElapsedLimit;
	}

	public long getTimeElapsedCount() {
		return timeElapsedCount;
	}

	public void setTimeElapsedCount(long timeElapsedCount) {
		this.timeElapsedCount = timeElapsedCount;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public TimeType getTimeType() {
		return timeType;
	}

	public void setTimeType(TimeType timeType) {
		this.timeType = timeType;
		
	}
// -----------------------------------------------------------------------------------	

}