package com.redru.engine.drawhandlers;

import com.redru.engine.wrapper.models.Model;

public interface IntDrawHandler {
	
	public void setup(Model model);

	public void updateTransformBuffers(float[] scalationMatrix, float[] rotationMatrix, float[] translationMatrix);
	
	public void draw(Model model);
	
}
