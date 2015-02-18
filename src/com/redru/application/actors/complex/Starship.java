package com.redru.application.actors.complex;

import android.util.Log;

import com.redru.engine.drawhandlers.IntDrawHandler;
import com.redru.engine.elements.GameActor;
import com.redru.engine.wrapper.models.Model;

public class Starship extends GameActor {
	private static final String TAG = "Starship";
	
// CONSTRUCTORS ----------------------------------------------------------------
	public Starship() {
		this(null, null, "");
	}
	
	public Starship(Model obj, String identifier) {
		this(obj, null, identifier);
	}
	
	public Starship(Model obj, IntDrawHandler drawHandler, String identifier) {
		super(obj, drawHandler, identifier);
		Log.i(TAG, "Creation complete.");
	}
// FUNCTIONS -------------------------------------------------------------------
	
// SETTERS AND GETTERS ---------------------------------------------------------
	
// -----------------------------------------------------------------------------
}
