package com.redru.engine.scene;

/**
 * Created by Luca on 20/01/2015.
 */
public interface IntSceneElement {
	
	public void scale(float xScale, float yScale, float zScale);
    
    public void rotate();

    public void translate(float xUpset, float yUpset, float zUpset);
    
    public void move();
    
    public void setStartingPositions(float startX, float startY, float startZ);
    
    public void toStartingPositions();

	public void setup();

    public void draw();

}
