package com.redru.application.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.redru.application.scene.complex.Starship;
import com.redru.engine.actions.IntAction;
import com.redru.engine.view.Camera;

public class SensorInputAction implements IntAction, SensorEventListener {
	private static final String TAG = "SensorInputHandler";

	private static SensorInputAction instance;
	DecimalFormat df = new DecimalFormat();
	
	private Starship starship;
	private float axisX = 0.0f, axisY = 0.0f, axisZ = 0.0f;
	
	private SensorInputAction() {
		df.setMaximumFractionDigits(3);
	}
	
	@Override
	public void execute(ArrayList<?> actionObjects) {
		Camera.getInstance().move(-axisY / 2, 0.0f, 0.0f);
		starship = (Starship) actionObjects.get(0);
		starship.translate(-axisY / 2, 0.0f, 0.0f);
		starship.getDrawHandler().updateBuffers();       
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		this.axisX = event.values[0];
		this.axisY = event.values[1];
		this.axisZ = event.values[2];
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) { }
	
	public static SensorInputAction getInstance() {
		if (instance == null) {
			instance = new SensorInputAction();
		}
		
		return instance;
	}

}
