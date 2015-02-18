package com.redru.engine.time;

import java.util.ArrayList;

import android.util.Log;

public final class TimeManager {
	private static final String TAG = "TimeManager";
	private static TimeManager instance;
	
	private ArrayList<TimeObject> timeObjects = new ArrayList<TimeObject>();
// CONSTRUCTOR -------------------------------------------------------------------------------------
	private TimeManager() {
		Log.i(TAG, "Creation complete.");
	}
	
	public static TimeManager getInstance() {
		if (instance == null) {
			instance = new TimeManager();
		}
		
		return instance;
	}
// FUNCTIONS ---------------------------------------------------------------------------------------
	public void addTimeObjectToList(TimeObject timeObject) {
		this.timeObjects.add(timeObject);
	}
	
	public void removeTimeObjectFromList(String identifier) {
		for (TimeObject tmp : this.timeObjects) {
			if (tmp.getIdentifier().equals(identifier)) {
				this.timeObjects.remove(tmp);
				break;
			}
		}
	}
	
	public void removeTimeObjectFromList(TimeObject timeObject) {
		for (TimeObject tmp : this.timeObjects) {
			if (tmp == timeObject) {
				this.timeObjects.remove(tmp);
				break;
			}
		}
	}
	
	public void updateTimeObjects() {
		for (TimeObject tmp : this.timeObjects) {
			if (tmp.isActive()) {
				tmp.update();
			}
		}
	}
// GETTERS AND SETTERS -----------------------------------------------------------------------------
	public ArrayList<TimeObject> getTimeObjects() {
		return this.timeObjects;
	}

	public void setTimeObjects(ArrayList<TimeObject> timeObjects) {
		this.timeObjects = timeObjects;
	}
// -------------------------------------------------------------------------------------------------
}
