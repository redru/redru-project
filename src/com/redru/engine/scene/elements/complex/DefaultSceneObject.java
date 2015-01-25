package com.redru.engine.scene.elements.complex;

import android.util.Log;

import com.redru.engine.scene.SceneElement;
import com.redru.engine.wrapper.EvoObj;

public class DefaultSceneObject implements SceneElement {
	private static String TAG = "DefaultSceneObject";
	
	private EvoObj obj;
	private EvoObjDrawHandler drawHandler;
	
	private float xUpset = 0.0f, yUpset = 0.0f, zUpset = 0.0f;
	private float xVel = 0.0f, yVel = 0.0f, zVel = 0.0f;

	/**
	 * 
	 */
	public DefaultSceneObject() {
		this(null, "");
	}
	
	/**
	 * 
	 * @param obj
	 * @param name
	 */
	public DefaultSceneObject(EvoObj obj, String name) {
		this.obj = obj;
		this.drawHandler = new EvoObjDrawHandler(obj);
	}

	/**
	 * 
	 */
	@Override
	public void setup() {
		drawHandler.setup();
	}

	/**
	 * 
	 */
	@Override
	public void draw() {
		drawHandler.draw();
	}

	/**
	 * 
	 */
	@Override
	public void translate(float xUpset, float yUpset, float zUpset) {
		Log.i(TAG, "Starting " + this.obj.getName() + " translation.");
    	this.xUpset += xUpset;
    	this.yUpset += yUpset;
    	this.zUpset += zUpset;
    	Log.i(TAG, "New positions for " + this.obj.getName() + ":" +
    			   "\nX: " + this.xUpset +
    			   "\nY: " + this.yUpset +
    			   "\nZ: " + this.zUpset);
    	
		obj.translate(xUpset, yUpset, zUpset);
	}

	/**
	 * 
	 */
	@Override
	public void rotate() {
		obj.rotate();
	}
	
	/**
	 * 
	 * @return
	 */
	public EvoObj getObj() {
		return obj;
	}

	/**
	 * 
	 * @param obj
	 */
	public void setObj(EvoObj obj) {
		this.obj = obj;
	}

	/**
	 * 
	 * @return
	 */
	public EvoObjDrawHandler getDrawHandler() {
		return drawHandler;
	}

	/**
	 * 
	 */
	public void resetDrawHandler() {
		this.drawHandler = new EvoObjDrawHandler(this.obj);
	}

	/**
	 * 
	 * @return
	 */
	public float getxVel() {
		return xVel;
	}

	/**
	 * 
	 * @param xVel
	 */
	public void setxVel(float xVel) {
		this.xVel = xVel;
	}

	/**
	 * 
	 * @return
	 */
	public float getyVel() {
		return yVel;
	}

	/**
	 * 
	 * @param yVel
	 */
	public void setyVel(float yVel) {
		this.yVel = yVel;
	}

	/**
	 * 
	 * @return
	 */
	public float getzVel() {
		return zVel;
	}

	/**
	 * 
	 * @param zVel
	 */
	public void setzVel(float zVel) {
		this.zVel = zVel;
	}

	/**
	 * 
	 * @return
	 */
	public float getxUpset() {
		return xUpset;
	}

	/**
	 * 
	 * @return
	 */
	public float getyUpset() {
		return yUpset;
	}

	/**
	 * 
	 * @return
	 */
	public float getzUpset() {
		return zUpset;
	}
	
}
