package com.redru.engine.elements;

import com.redru.engine.drawhandlers.IntDrawHandler;
import com.redru.engine.wrapper.models.Model;

public abstract class CollisionGameActor extends GameActor implements IntCollision {
	public static final int MIN_X = 0;
	public static final int MAX_X = 1;
	public static final int MIN_Y = 2;
	public static final int MAX_Y = 3;
	public static final int MIN_Z = 4;
	public static final int MAX_Z = 5;
// CONSTRUCTOR --------------------------------------------------------------------------------------------
	public CollisionGameActor() {
		super(null, null, "");
	}
	
	public CollisionGameActor(Model model, String identifier) {
		super(model, null, identifier);
	}
	
	public CollisionGameActor(Model model, IntDrawHandler drawHandler, String identifier) {
		super(model, drawHandler, identifier);
	}
// IMPLEMENTATIONS ----------------------------------------------------------------------------------------
	@Override
	public final boolean checkCollision(CollisionGameActor actor) {
		if ( 
				(super.collisionInfo[MIN_X] >= actor.collisionInfo[MIN_X] && super.collisionInfo[MIN_X] <= actor.collisionInfo[MAX_X]  ||
				 super.collisionInfo[MAX_X] >= actor.collisionInfo[MIN_X] && super.collisionInfo[MAX_X] <= actor.collisionInfo[MAX_X]) &&
				(super.collisionInfo[MIN_Y] >= actor.collisionInfo[MIN_Y] && super.collisionInfo[MIN_Y] <= actor.collisionInfo[MAX_Y]  ||
				 super.collisionInfo[MAX_Y] >= actor.collisionInfo[MIN_Y] && super.collisionInfo[MAX_Y] <= actor.collisionInfo[MAX_Y]) &&
				(super.collisionInfo[MIN_Z] >= actor.collisionInfo[MIN_Z] && super.collisionInfo[MIN_Z] <= actor.collisionInfo[MAX_Z]  ||
				 super.collisionInfo[MAX_Z] >= actor.collisionInfo[MIN_Z] && super.collisionInfo[MAX_Z] <= actor.collisionInfo[MAX_Z])
		) {
			this.onCollision(actor); // Execute onCollision each object, passing the other as argument
			actor.onCollision(this);
			return true;
		}
		
		return false;
	}
	
	@Override
	public abstract void onCollision(CollisionGameActor actor);
// --------------------------------------------------------------------------------------------------------
}
