package com.redru.application.actions;

import android.view.MotionEvent;

import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;

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
//            	Obj tmp = ObjFactory.getInstance().getStockedObject("SimpleBullet");
//            	Bullet bullet = new Bullet(tmp, new TriangleObjDrawHandler(tmp), "SimpleBullet");
//            	bullet.setStartPosition(0.0f, 0.0f, 0.0f);
//            	SceneContext.getInstance().addElementToScene(bullet);
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
	}
// -----------------------------------------------------------------------------------------
}
