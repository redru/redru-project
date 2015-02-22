package com.redru.engine.elements;

public interface IntCollision {

	public boolean checkCollision(CollisionGameActor actor);
	
	public void onCollision(CollisionGameActor actor);
	
}
