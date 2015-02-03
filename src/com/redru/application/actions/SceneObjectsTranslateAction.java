package com.redru.application.actions;

import java.util.ArrayList;

import android.util.Log;

import com.redru.application.scene.complex.StarshipObject;
import com.redru.engine.actions.IntAction;

public class SceneObjectsTranslateAction implements IntAction {
	private static final String TAG = "SceneObjectsTranslateAction";

	private static SceneObjectsTranslateAction instance;
	
	private SceneObjectsTranslateAction() { Log.i(TAG, "Creation complete."); }
	
	@Override
	public void execute(ArrayList<?> actionObjects) {
		for (Object element : actionObjects) {
        	if (!((StarshipObject) element).getIdentifier().equals("B-2 Spirit")) {
        		if (((StarshipObject) element).getzUpset() > -((StarshipObject) element).getStartZ() - 10.0f) {
        			((StarshipObject) element).translate(0.0f, 0.0f, -1.0f);
        		} else {
        			((StarshipObject) element).toStartingPositions();
        		}
        	}
        }
	}
	
	public static SceneObjectsTranslateAction getInstance() {
		if (instance == null) {
			instance = new SceneObjectsTranslateAction();
		}
		
		return instance;
	}

}
