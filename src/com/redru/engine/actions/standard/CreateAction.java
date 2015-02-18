package com.redru.engine.actions.standard;

import com.redru.application.scene.complex.Starship;
import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.elements.GameActor;
import com.redru.engine.scene.SceneContext;
import com.redru.engine.wrapper.models.Model;
import com.redru.engine.wrapper.models.ModelFactory;
import com.redru.engine.wrapper.textures.TextureFactory;

public final class CreateAction extends Action {
	private static CreateAction instance;
	
	private GameActor actor;
	private CreationObject creatObj;
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
			creatObj = (CreationObject) tmp;
			Model tmpModel = ModelFactory.getInstance().getStockedModel(this.creatObj.modelIdentifier);
			tmpModel.setTexture(TextureFactory.getInstance().getStockedTexture("tex_b2spirit"));
			actor = new Starship(tmpModel, "Enemy x");
			actor.scale(this.creatObj.xSca, this.creatObj.ySca, this.creatObj.zSca);
	    	actor.rotate(this.creatObj.xRot, this.creatObj.yRot, this.creatObj.zRot);
	    	actor.translate(this.creatObj.xPos, this.creatObj.yPos, this.creatObj.zPos);
	    	actor.setStaticPosition(this.creatObj.xPos, this.creatObj.yPos, this.creatObj.zPos);
	    	actor.getDrawHandler().updateTransformBuffers();
	    	
			scene.addElementToScene(actor);
			actor.getDrawHandler().updateTransformBuffers();
		}
		
		context.getValues().clear();
	}
// -------------------------------------------------------------
}
