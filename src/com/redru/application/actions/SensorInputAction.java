package com.redru.application.actions;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.redru.application.scene.complex.Starship;
import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.view.Camera;

public class SensorInputAction extends Action implements SensorEventListener {
	private static final String TAG = "SensorInputHandler";

	private static SensorInputAction instance;
	
	private Starship starship;
	private float axisX = 0.0f, axisY = 0.0f, axisZ = 0.0f;
	
	private SensorInputAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
		Log.i(TAG, "Creation complete.");
	}
	
	@Override
	public void execute(ActionContext<?> context) {
		for (Object element : context.getValues()) {
			starship = (Starship) element;
        	if (starship.getIdentifier().equals("B-2 Spirit")) {
				if (starship.getxPos() - (axisY / 2) < 30 && starship.getxPos() - (axisY / 2) > -30) {
					starship.translate(-axisY / 2, 0.0f, 0.0f);
					
					Camera.getInstance().move(-axisY / 2, 0.0f, 0.0f);
				}
				
				starship.getDrawHandler().updateTransformBuffers();
				break;
        	}
		}
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
			instance = new SensorInputAction("SensorInputAction", false);
		}
		
		return instance;
	}

}
