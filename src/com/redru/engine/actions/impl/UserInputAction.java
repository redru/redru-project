package com.redru.engine.actions.impl;

import java.util.ArrayList;

import android.graphics.Point;

import com.redru.engine.actions.IntAction;
import com.redru.engine.input.UserInputHandler;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.view.Camera;

public class UserInputAction implements IntAction {
	
	public UserInputAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ArrayList<?> actionObjects) {
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
            	((IntSceneElement) actionObjects.get(0)).translate(0.8f, 0.0f, 0.0f);
            } else {
            	Camera.getInstance().move(-0.8f, 0.0f, 0.0f);
            	((IntSceneElement) actionObjects.get(0)).translate(-0.8f, 0.0f, 0.0f);
            }
        }
	}

}
