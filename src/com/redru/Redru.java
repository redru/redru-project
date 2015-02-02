package com.redru;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;

import com.redru.engine.GLView;
import com.redru.engine.actions.ActionsManager;
import com.redru.engine.actions.impl.SensorInputAction;
import com.redru.engine.input.UserInputHandler;
import com.redru.engine.scene.SceneContext;
import com.redru.engine.view.Camera;
import com.redru.engine.wrapper.ObjFactory;
import com.redru.engine.wrapper.TextureFactory;

public class Redru extends Activity {
	
	public static final String TAG = "MainActivity";
    private static Context context;
    
	private SensorManager sensorManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    context = this;
	    
	    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    sensorManager.registerListener(SensorInputAction.getInstance(), sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        
        // Starting Application Framework
        Camera.getInstance();
        ObjFactory.getInstance();
        TextureFactory.getInstance();
        SceneContext.getInstance();
        UserInputHandler.getInstance();
        ActionsManager.getInstance();

        setContentView(new GLView(this));
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
		super.onPause();
	    sensorManager.unregisterListener(SensorInputAction.getInstance());
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    sensorManager.registerListener(SensorInputAction.getInstance(), sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        return UserInputHandler.getInstance().handleMotionEvent(event);
    }

    /**
     * 
     * @return
     */
    public static Context getContext() {
        return context;
    }
    
}
