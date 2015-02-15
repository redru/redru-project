package com.redru.application.actions;

import android.graphics.Point;

import com.redru.application.input.UserInputHandler;
import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.elements.IntTransformable;
import com.redru.engine.view.Camera;

public class UserInputAction extends Action {

	public UserInputAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
	}

	@Override
	public void execute(ActionContext<?> context) {
		if (!UserInputHandler.getInstance().isRotationHandled()) {
            Point rotation = UserInputHandler.getInstance().getRotation();
            
            if (rotation.x > 0) {
                rotation.x = -2;
            } else if (rotation.x < 0) {
                rotation.x = 2;
            }

            if (rotation.y > 0) {
                rotation.y = -2;
            } else if (rotation.y < 0) {
                rotation.y = 2;
            }

            if (rotation.x > 0) {
            	Camera.getInstance().move(0.8f, 0.0f, 0.0f);
            	((IntTransformable) context.getValues().get(0)).translate(0.8f, 0.0f, 0.0f);
            } else {
            	Camera.getInstance().move(-0.8f, 0.0f, 0.0f);
            	((IntTransformable) context.getValues().get(0)).translate(-0.8f, 0.0f, 0.0f);
            }
        }
	}

}
