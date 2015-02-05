package com.redru.application.actions;

import java.util.ArrayList;

import android.util.Log;

import com.redru.application.scene.complex.StarshipObject;
import com.redru.engine.actions.IntAction;

public class SceneObjectsTranslateAction implements IntAction {
	private static final String TAG = "SceneObjectsTranslateAction";

	private static SceneObjectsTranslateAction instance;
	private StarshipObject starship;
	
	private SceneObjectsTranslateAction() { Log.i(TAG, "Creation complete."); }
	
	@Override
	public void execute(ArrayList<?> actionObjects) {
		for (Object element : actionObjects) {
			starship = (StarshipObject) element;
        	if (!(starship.getIdentifier().equals("B-2 Spirit"))) {
        		if (starship.getzPos() > -starship.getStartZ() - 10.0f) {
//        			starship.translate(0.0f, 0.0f, -1.0f);
        			starship.rotateAndTranslate(5, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f);
        		} else {
        			starship.translate( -starship.getxPos(), -starship.getyPos(), -starship.getzPos());
//        			starship.rotateAndTranslate(5, 0.0f, 0.0f, 1.0f, -starship.getxPos(), -starship.getyPos(), -starship.getzPos());
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
