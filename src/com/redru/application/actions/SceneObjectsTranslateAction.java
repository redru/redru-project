package com.redru.application.actions;

import android.util.Log;

import com.redru.application.scene.complex.Starship;
import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;

public class SceneObjectsTranslateAction extends Action {
	private static final String TAG = "SceneObjectsTranslateAction";

	private static SceneObjectsTranslateAction instance;
	private Starship starship;
	
	private SceneObjectsTranslateAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
		Log.i(TAG, "Creation complete.");
	}
	
	@Override
	public void execute(ActionContext<?> context) {
		for (Object element : context.getValues()) {
			starship = (Starship) element;
        	if (!(starship.getIdentifier().equals("B-2 Spirit"))) {
        		if (starship.getzPos() > -starship.getzStart() - 10.0f) {
        			starship.translate(0.0f, 0.0f, -1.0f);
        		} else {
        			starship.translate(0.0f, 0.0f, -starship.getzPos() * 2);
        		}
        	}
        	
        	starship.getDrawHandler().updateBuffers();
        }
	}
	
	public static SceneObjectsTranslateAction getInstance() {
		if (instance == null) {
			instance = new SceneObjectsTranslateAction("SceneObjectsTranslateAction", false);
		}
		
		return instance;
	}

}
