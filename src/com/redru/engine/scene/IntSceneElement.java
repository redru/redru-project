package com.redru.engine.scene;

/**
 * Created by Luca on 20/01/2015.
 */
public interface IntSceneElement {

    /**
     * Object setup method
     */
	public void setup();

	/**
	 * Object draw method
	 */
    public void draw();
    
    /**
     * Update buffers in the drahandler
     */
    public void updateTransformBuffers();

}
