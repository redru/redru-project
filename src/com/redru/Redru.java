package com.redru;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;

import com.redru.application.GLView;
import com.redru.application.actions.SensorInputAction;
import com.redru.application.actions.TouchInputAction;
import com.redru.engine.actions.ActionContext;

public class Redru extends Activity {
	
	public static final String TAG = "MainActivity";
    private static Context context;
    
    private GLView glView;
	private SensorManager sensorManager;
	private ActionContext<MotionEvent> cont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    context = this;
	    
	    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    sensorManager.registerListener(SensorInputAction.getInstance(), sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

	    glView = new GLView(this);
        setContentView(glView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.redru, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onPause() {
	    sensorManager.unregisterListener(SensorInputAction.getInstance());
	    super.onPause();
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    sensorManager.registerListener(SensorInputAction.getInstance(), sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
	    glView.onResume();
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		if (this.cont == null) {
			ArrayList<MotionEvent> touchEvent = new ArrayList<MotionEvent>();
			touchEvent.add(0, event);
			this.cont = new ActionContext<MotionEvent>("TouchInputContext", touchEvent, true);
		} else {
			this.cont.getValues().set(0, event);
		}
		
		TouchInputAction.getInstance().execute(this.cont);
        return true;
    }

    /**
     * 
     * @return
     */
    public static Context getContext() {
        return context;
    }
    
}
