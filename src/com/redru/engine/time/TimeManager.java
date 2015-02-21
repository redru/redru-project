package com.redru.engine.time;

import java.util.ArrayList;

import android.util.Log;

public final class TimeManager {
	private static final String TAG = "TimeManager";
	private static TimeManager instance;
	
	private long timeA;
	private long timeB;
	private long timeElapsed;
	
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
	/**
	 * Add the TimeObject argument the the list.
	 * @param timeObject
	 */
	public void addTimeObjectToList(TimeObject timeObject) {
		this.timeObjects.add(timeObject); // Add the specified TimeObject
	}
	
	/**
	 * If present, removes the TimeObject from the list.
	 * @param identifier
	 */
	public void removeTimeObjectFromList(String identifier) {
		for (TimeObject tmp : this.timeObjects) {
			if (tmp.getIdentifier().equals(identifier)) { // Check if identifiers equals
				this.timeObjects.remove(tmp); // Removes the TimeObject from the list
				break;
			}
		}
	}
	
	/**
	 * Updates all time objects checking the TimeType.
	 */
	public void updateTimeObjects() {
		this.timeA = System.nanoTime(); // Get the current time
		this.timeElapsed = (this.timeA - this.timeB) / TimeUtils.MICROSECOND; // Time elapsed from the last cycle (in milliseconds)
		
		if (this.timeElapsed < 10000L) { // It should be FALSE only the first time we execute this method after the TimeManager creation
			for (TimeObject tmp : this.timeObjects) {
				if (tmp.isActive()) { // Update only if the TimeObject isActive()
					if (tmp.equals(TimeObject.TimeType.FRAME_BASED)) {
						tmp.updateFrameTime(); // Update method for the FRAME_BASED TimeType
					} else if (tmp.equals(TimeObject.TimeType.ELAPSED_TIME_BASED)) {
						tmp.updateElapsedTime(timeElapsed); // Update method for the ELAPSED_TIME_BASED TimeType passing the timeElapsed as an argument
					}
				}
			}
		}
			
		this.timeB = System.nanoTime(); // Get the current time
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
