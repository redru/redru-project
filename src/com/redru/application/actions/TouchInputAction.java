package com.redru.application.actions;

import android.view.MotionEvent;

import com.redru.application.actors.complex.Bullet;
import com.redru.application.actors.complex.Bullet.BulletType;
import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.actions.ActionsManager;
import com.redru.engine.audio.AudioController;
import com.redru.engine.drawhandlers.SimpleDrawHandler;
import com.redru.engine.elements.TransformableGameActor;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.scene.SceneContext;
import com.redru.engine.wrapper.models.Model;
import com.redru.engine.wrapper.models.ModelFactory;

public class TouchInputAction extends Action {
	private static TouchInputAction instance;
	private MotionEvent event;
	
	private Model tmpModel;
	private TransformableGameActor actor;
	private TransformableGameActor tmpActor;
	private ActionsManager actionsManager;
	private AudioController audioManager;
	private float lastTime = System.nanoTime() / 1000000000f;
	private float tmpTime = System.nanoTime() / 1000000000f;
// CONSTRUCTOR -----------------------------------------------------------------------------
	private TouchInputAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
		this.actionsManager = ActionsManager.getInstance();
		this.audioManager = AudioController.getInstance();
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
            	this.tmpTime = System.nanoTime() / 1000000000f; // Get current time
            	if (this.tmpTime - this.lastTime > 0.15f) { // Only if the established time is passed
            		this.lastTime = this.tmpTime; // Update last time
	            	for (IntSceneElement tmp : SceneContext.getInstance().getElements()) { // Find the main starship position
	            		this.tmpActor = (TransformableGameActor) tmp;
	            		
	            		if (tmpActor.getIdentifier().equals("B-2 Spirit")) {
		            		this.tmpModel = ModelFactory.getInstance().getStockedModel("cust_simple_bullet");
		                	this.actor = new Bullet(this.tmpModel, new SimpleDrawHandler(), "Bullet", 5, BulletType.SIMPLE);
		                	this.actor.scale(1.0f, 1.0f, 1.0f);
		                	this.actor.rotate(0.0f, 0.0f, 0.0f);
		                	this.actor.translate(tmpActor.getxPos(), tmpActor.getyPos(), tmpActor.getzPos() + 1.0f);
		                	this.actor.setStaticPosition(tmpActor.getxPos(), tmpActor.getyPos(), tmpActor.getzPos());
		                	
		                	this.actionsManager.addObjectToCreationQueue(this.actor);
		                	this.audioManager.playSound("SHOT");
		                	break;
	            		}
	            	}
	            	
	            	this.tmpModel = null;
	            	this.actor = null;
            	}
            	
                break;

            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
	}
// -----------------------------------------------------------------------------------------
}
