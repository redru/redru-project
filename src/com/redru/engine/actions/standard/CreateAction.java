package com.redru.engine.actions.standard;

import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.elements.GameActor;
import com.redru.engine.scene.SceneContext;

public final class CreateAction extends Action {
	private static CreateAction instance;
	
	private GameActor actor;
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
			this.actor = (GameActor) tmp;
	    	
			this.actor.setup();
			this.actor.updateTransformBuffers();
			this.scene.addElementToScene(this.actor);
		}
		
		this.actor = null;
		context.getValues().clear();
	}
// -------------------------------------------------------------
}
