package com.redru.engine.actions.standard;

import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.scene.SceneContext;

public final class DestroyAction extends Action {
	private static DestroyAction instance;
	
	private IntSceneElement sceneElement;
	private SceneContext scene;
	
	private DestroyAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
		this.scene = SceneContext.getInstance();
	}
	
	public static DestroyAction getInstance() {
		if (instance == null) {
			instance = new DestroyAction("DestroyAction", false);
		}
		
		return instance;
	}
// -------------------------------------------------------------
	@Override
	public void execute(ActionContext<?> context) {
		for (Object tmp : context.getValues()) {
			sceneElement = (IntSceneElement) tmp;
			scene.removeElementFromScene(sceneElement);
		}
		
		context.getValues().clear();
	}
// -------------------------------------------------------------
}
