package com.redru.application.actions;

import java.util.ArrayList;

import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.elements.CollisionGameActor;

public class CollisionCheckAction extends Action {
	private CollisionGameActor actor;
	private ArrayList<CollisionGameActor> actorsList = new ArrayList<CollisionGameActor>();
	
// CONSTRUCTOR ----------------------------------------------------------------------------
	public CollisionCheckAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
		// TODO Auto-generated constructor stub
	}
// IMPLEMENTATION -------------------------------------------------------------------------
	@Override
	public void execute(ActionContext<?> context) {
		for (Object element : context.getValues()) {
			if (element instanceof CollisionGameActor) {
				
				if (((CollisionGameActor) element).getIdentifier().equals("B-2 Spirit")) {
					actor = (CollisionGameActor) element;
	        	} else {
	        		actorsList.add((CollisionGameActor) element);
	        	}
			}
		}
		
		for (CollisionGameActor tmp : actorsList) {
			for (CollisionGameActor tmpSec : actorsList) {
				if (tmp != tmpSec) {
					tmp.checkCollision(tmpSec);
				}
			}
		}
		
		actor = null;
		actorsList.clear();
	}
// ----------------------------------------------------------------------------------------
}
