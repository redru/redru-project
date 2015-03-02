package com.redru.engine.actions.standard;

import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.context.EngineContext;
import com.redru.engine.elements.TransformableGameActor;
import com.redru.engine.scene.SceneContext;

public final class CreateAction extends Action {
	private static CreateAction instance;
	
	private TransformableGameActor actor;
	
	private CreateAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
		EngineContext.scene = SceneContext.getInstance();
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
			this.actor = (TransformableGameActor) tmp;
	    	
			this.actor.setup();
			this.actor.updateTransformBuffers();
			EngineContext.scene.addElementToScene(this.actor);
		}
		
		this.actor = null;
		context.getValues().clear();
	}
// -------------------------------------------------------------
}
