package com.redru.application.actions;

import java.util.Random;

import android.view.MotionEvent;

import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.actions.ActionsManager;
import com.redru.engine.actions.standard.CreationObject;

public class TouchInputAction extends Action {
	private static TouchInputAction instance;

// CONSTRUCTOR -----------------------------------------------------------------------------
	private TouchInputAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
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
		MotionEvent event = (MotionEvent) context.getValues().get(0);
		int action = event.getAction();
		
        switch (action) {

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_DOWN:
            	Random rand = new Random();
            	
            	float x = rand.nextFloat() * 21.0f - 10.0f;
            	float y = rand.nextFloat() * 7.0f - 3.0f;
            	float z = rand.nextFloat() * 201.0f;
            	
            	CreationObject tmp = new CreationObject("obj_b2spirit", x, y, z, 1.0f, 1.0f, 1.0f, 0.0f, 180.0f, 0.0f);
            	ActionsManager.getInstance().addObjectToCreationQueue(tmp);
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
	}
// -----------------------------------------------------------------------------------------
}
