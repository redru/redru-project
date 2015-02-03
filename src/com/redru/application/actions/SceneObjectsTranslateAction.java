package com.redru.application.actions;

import java.util.ArrayList;

import com.redru.application.scene.complex.ComplexSceneObject;
import com.redru.engine.actions.IntAction;

public class SceneObjectsTranslateAction implements IntAction {
	private static final String TAG = "SceneObjectsTranslateAction";

	private static SceneObjectsTranslateAction instance;
	
	private SceneObjectsTranslateAction() {
		
	}
	
	@Override
	public void execute(ArrayList<?> actionObjects) {
		for (Object element : actionObjects) {
        	if (!((ComplexSceneObject) element).getObj().getName().equals("B-2 Spirit")) {
        		((ComplexSceneObject) element).translate(0.0f, 0.0f, -1.0f);
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
