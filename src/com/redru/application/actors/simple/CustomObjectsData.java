package com.redru.application.actors.simple;

import android.util.Log;

public class CustomObjectsData {
	private static final String TAG = "CustomObjectsData";
	
	private static CustomObjectsData instance;
	
// CONSTRUCTOR -----------------------------------------------------------------------------------
	private CustomObjectsData() {
		Log.i(TAG, "Creation complete.");
	}
	
	public static CustomObjectsData getInstance() {
		if (instance == null) {
			instance = new CustomObjectsData();
		}
		
		return instance;
	}
// DATA -------------------------------------------------------------------------------------------
	
	public float[] simpleBulletData = {
		/*POS*/ 0.0f, 0.0f,  0.3f, /*COLS*/0.0f, 1.0f, 0.0f, 0.5f,
		/*POS*/ 0.2f, 0.0f, -0.3f, /*COLS*/0.0f, 1.0f, 0.0f, 0.5f,
		/*POS*/-0.2f, 0.0f, -0.3f, /*COLS*/0.0f, 1.0f, 0.0f, 0.5f
	};
	
}
