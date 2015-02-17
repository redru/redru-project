package com.redru.engine.actions.standard;

import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.scene.SceneContext;

public final class CreateAction extends Action {
	private static CreateAction instance;
	
	private IntSceneElement sceneElement;
	private SceneContext scene;
	
	private CreateAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
		this.scene = SceneContext.getInstance();
	}
	
	public static CreateAction getInstance() {
		if (instance == null) {
			instance = new CreateAction("CreateAction", false);
		}
		
		return instance;
	}
// -------------------------------------------------------------
	@Override
	public void execute(ActionContext<?> context) {
		for (Object tmp : context.getValues()) {
			sceneElement = (IntSceneElement) tmp;
			scene.addElementToScene(sceneElement);
		}
		
		context.getValues().clear();
	}
// -------------------------------------------------------------
}
