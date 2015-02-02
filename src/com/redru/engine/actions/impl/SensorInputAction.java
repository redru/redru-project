package com.redru.engine.actions.impl;

import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.redru.engine.actions.IntAction;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.view.Camera;

public class SensorInputAction implements IntAction, SensorEventListener {
	private static final String TAG = "SensorInputHandler";

	private static SensorInputAction instance;
	private float axisX = 0.0f, axisY = 0.0f, axisZ = 0.0f;
	
	private SensorInputAction() {
		
	}
	
	@Override
	public void execute(ArrayList<?> actionObjects) {
		Camera.getInstance().move(-axisY / 2, 0.0f, 0.0f);
        ((IntSceneElement) actionObjects.get(0)).translate(-axisY / 2, 0.0f, 0.0f);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		this.axisX = event.values[0];
		this.axisY = event.values[1];
		this.axisZ = event.values[2];
	    
	    Log.i(TAG, "axisX: " + this.axisX + "\naxisY: " + this.axisY + "\naxisZ: " + this.axisZ);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//SensorInputAction.getInstance().handle(sensor, accuracy);
		
	}
	
	public static SensorInputAction getInstance() {
		if (instance == null) {
			instance = new SensorInputAction();
		}
		
		return instance;
	}

}
