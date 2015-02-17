package com.redru.engine.elements;

public interface IntTransformable {

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
	 * 
	 * @param angle
	 * @param xAxis
	 * @param yAxis
	 * @param zAxis
	 */
    public void rotate(float xAxis, float yAxis, float zAxis);
    
    /**
     * Translate the object by the values given in the method
     * 
     * @param xUpset
     * @param yUpset
     * @param zUpset
     */
    public void translate(float xUpset, float yUpset, float zUpset);
    
    /**
     * 
     * @param xUpset
     * @param yUpset
     * @param zUpset
     */
    public void translateToPosition(float xUpset, float yUpset, float zUpset);
	
}
