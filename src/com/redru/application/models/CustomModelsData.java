package com.redru.application.models;

import android.util.Log;

public class CustomModelsData {
	private static final String TAG = "CustomObjectsData";
	
	private static CustomModelsData instance;
	
// CONSTRUCTOR -----------------------------------------------------------------------------------
	private CustomModelsData() {
		Log.i(TAG, "Creation complete.");
	}
	
	public static CustomModelsData getInstance() {
		if (instance == null) {
			instance = new CustomModelsData();
		}
		
		return instance;
	}
// DATA -------------------------------------------------------------------------------------------
	
	public float[] simpleBulletData = {
		/*POS*/ 0.0f, 0.0f,  0.3f, /*COLS*/0.0f, 1.0f, 0.0f, 0.5f,
		/*POS*/ 0.2f, 0.0f, -0.3f, /*COLS*/0.0f, 1.0f, 0.0f, 0.5f,
		/*POS*/-0.2f, 0.0f, -0.3f, /*COLS*/0.0f, 1.0f, 0.0f, 0.5f
	};
	
	public float[] simpleBulletMinMax = { -0.2f, 0.2f, 0.0f, 0.0f, -0.3f, 0.3f };
	
}
