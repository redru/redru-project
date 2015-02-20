package com.redru.application.actions;

import android.util.Log;

import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.actions.ActionsManager;
import com.redru.engine.elements.GameActor;

public class SceneObjectsTranslateAction extends Action {
	private static final String TAG = "SceneObjectsTranslateAction";

	private static SceneObjectsTranslateAction instance;
	private GameActor actor;
	
	private SceneObjectsTranslateAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
		Log.i(TAG, "Creation complete.");
	}
	
	@Override
	public void execute(ActionContext<?> context) {
		for (Object element : context.getValues()) {
			this.actor = (GameActor) element;
			if (actor.getIdentifier().contains("Bullet")) {				
				if (this.actor.getzPos() > 200.0f) {
	        		ActionsManager.getInstance().addObjectToDestroyQueue(this.actor);
	        	} else {
	        		this.actor.translate(0.0f, 0.0f, 1.7f);
	        	}
				
				this.actor.getDrawHandler().updateTransformBuffers();
			} else if (actor.getIdentifier().contains("Enemy")) {
				if (this.actor.getzPos() > - 20.0f) {
        			this.actor.translate(0.0f, 0.0f, -0.85f);
        		} else {
        			ActionsManager.getInstance().addObjectToDestroyQueue(this.actor);
        		}
	        	
	        	this.actor.getDrawHandler().updateTransformBuffers();
			}
        }
		
		this.actor = null;
	}
	
	public static SceneObjectsTranslateAction getInstance() {
		if (instance == null) {
			instance = new SceneObjectsTranslateAction("SceneObjectsTranslateAction", false);
		}
		
		return instance;
	}

}
