package com.redru.application.actions;

import android.view.MotionEvent;

import com.redru.application.actors.complex.Starship;
import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.actions.ActionsManager;
import com.redru.engine.drawhandlers.SimpleDrawHandler;
import com.redru.engine.elements.GameActor;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.scene.SceneContext;
import com.redru.engine.wrapper.models.Model;
import com.redru.engine.wrapper.models.ModelFactory;

public class TouchInputAction extends Action {
	private static TouchInputAction instance;
	private MotionEvent event;
	
	private Model tmpModel;
	private GameActor actor;
	private GameActor tmpActor;
	private ActionsManager actionsManager;
// CONSTRUCTOR -----------------------------------------------------------------------------
	private TouchInputAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
		this.actionsManager = ActionsManager.getInstance();
	}
	
	public static TouchInputAction getInstance() {
		if (instance == null) {
			instance = new TouchInputAction("TouchInputAction", false);
		}
		
		return instance;
	}
// METHOD IMPLEMENTATION -------------------------------------------------------------------
	@Override
	public void execute(ActionContext<?> context) {
		this.event = (MotionEvent) context.getValues().get(0);
		int action = event.getAction();
		
        switch (action) {

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_DOWN:
            	for (IntSceneElement tmp : SceneContext.getInstance().getElements()) {
            		this.tmpActor = (GameActor) tmp;
            		
            		if (tmpActor.getIdentifier().equals("B-2 Spirit")) {
	            		this.tmpModel = ModelFactory.getInstance().getStockedModel("cust_simple_bullet");
	                	this.actor = new Starship(this.tmpModel, "Bullet");
	                	this.actor.setDrawHandler(new SimpleDrawHandler(this.actor));
	                	this.actor.scale(1.0f, 1.0f, 1.0f);
	                	this.actor.rotate(0.0f, 0.0f, 0.0f);
	                	this.actor.translate(tmpActor.getxPos(), tmpActor.getyPos(), tmpActor.getzPos() + 1.0f);
	                	this.actor.setStaticPosition(tmpActor.getxPos(), 0, tmpActor.getzPos());
	                	
	                	this.actionsManager.addObjectToCreationQueue(this.actor);
	                	break;
            		}
            	}
            	
            	
            	
            	this.tmpModel = null;
            	this.actor = null;
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
	}
// -----------------------------------------------------------------------------------------
}
