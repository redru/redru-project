package com.redru.application.actors.complex;

import android.util.Log;

import com.redru.engine.drawhandlers.IntDrawHandler;
import com.redru.engine.elements.CollisionGameActor;
import com.redru.engine.wrapper.models.Model;

public class Starship extends CollisionGameActor {
	private static final String TAG = "Starship";
	
// CONSTRUCTORS ----------------------------------------------------------------
	public Starship() {
		super(null, null, "");
		Log.i(TAG, "Creation complete.");
	}
	
	public Starship(Model obj, String identifier) {
		super(obj, null, identifier);
		Log.i(TAG, "Creation complete.");
	}
	
	public Starship(Model obj, IntDrawHandler drawHandler, String identifier) {
		super(obj, drawHandler, identifier);
		Log.i(TAG, "Creation complete.");
	}
// FUNCTIONS -------------------------------------------------------------------
	@Override
	public void onCollision(CollisionGameActor actor) {
//		Log.i(TAG, super.getIdentifier() + " collision with " + actor.getIdentifier());
	}
// -----------------------------------------------------------------------------
}
