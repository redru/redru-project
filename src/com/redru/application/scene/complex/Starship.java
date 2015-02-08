package com.redru.application.scene.complex;

import android.util.Log;

import com.redru.engine.drawhandlers.IntDrawHandler;
import com.redru.engine.elements.BaseElement;
import com.redru.engine.wrapper.model.Obj;

public class Starship extends BaseElement {
	private static final String TAG = "Starship";
	
// CONSTRUCTORS ----------------------------------------------------------------
	public Starship() {
		this(null, null, "");
	}
	
	public Starship(Obj obj, String identifier) {
		this(obj, null, identifier);
	}
	
	public Starship(Obj obj, IntDrawHandler drawHandler, String identifier) {
		super(obj, drawHandler, identifier);
		Log.i(TAG, "Creation complete.");
	}
// FUNCTIONS -------------------------------------------------------------------
	
// SETTERS AND GETTERS ---------------------------------------------------------
	
// -----------------------------------------------------------------------------
}
