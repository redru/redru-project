package com.redru.engine.scene;

public interface IntDynamicElement {

	/**
	 * Scale the object by the values given in the method
	 * 
	 * @param xScale
	 * @param yScale
	 * @param zScale
	 */
	public void scale(float xScale, float yScale, float zScale);
    
	/**
	 * Rotate the object by the values given in the method
	 */
    public void rotate();

    /**
     * Translate the object by the values given in the method
     * 
     * @param xUpset
     * @param yUpset
     * @param zUpset
     */
    public void translate(float xUpset, float yUpset, float zUpset);
    
    /**
     * Executes any logic implemented for moving
     */
    public void move();
    
    /**
     * Set the object starting position
     * 
     * @param startX
     * @param startY
     * @param startZ
     */
    public void setStartingPositions(float startX, float startY, float startZ);
    
    /**
     * Translate to the starting position
     */
    public void toStartingPositions();
	
}
